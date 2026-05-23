package com.dorm.controller;

import com.dorm.domain.entity.DormAllocation;
import com.dorm.domain.entity.DormRoom;
import com.dorm.domain.entity.Student;
import com.dorm.repository.DormAllocationRepository;
import com.dorm.repository.DormRoomRepository;
import com.dorm.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/dorm")
public class AdminDormController {

    private final DormRoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final DormAllocationRepository allocationRepository;

    public AdminDormController(DormRoomRepository roomRepository,
                               StudentRepository studentRepository,
                               DormAllocationRepository allocationRepository) {
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
        this.allocationRepository = allocationRepository;
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> listRooms() {
        List<DormRoom> rooms = roomRepository.findAll();
        List<Map<String, Object>> result = rooms.stream().map(r -> {
            List<DormAllocation> allocs = allocationRepository.findByRoom_Id(r.getId());
            return Map.<String, Object>of(
                "id", r.getId(),
                "building", r.getBuilding(),
                "floor", r.getFloor(),
                "roomNo", r.getRoomNo(),
                "totalBeds", r.getTotalBeds(),
                "occupiedBeds", allocs.size(),
                "allocations", allocs.stream().map(a -> Map.<String, Object>of(
                    "id", a.getId(),
                    "studentId", a.getStudent().getId(),
                    "studentName", a.getStudent().getName(),
                    "studentNo", a.getStudent().getStudentNo(),
                    "bedNo", a.getBedNo() != null ? a.getBedNo() : ""
                )).collect(Collectors.toList())
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("data", result));
    }

    @GetMapping("/students")
    public ResponseEntity<?> listStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(Map.of("data", students));
    }

    @GetMapping("/allocations")
    public ResponseEntity<?> listAllocations() {
        List<DormAllocation> allocations = allocationRepository.findAll();
        List<Map<String, Object>> result = allocations.stream().map(a -> {
            DormRoom room = a.getRoom();
            Student student = a.getStudent();
            return Map.<String, Object>of(
                "id", a.getId(),
                "studentId", student.getId(),
                "studentNo", student.getStudentNo(),
                "studentName", student.getName(),
                "building", room.getBuilding(),
                "roomNo", room.getRoomNo(),
                "bedNo", a.getBedNo() != null ? a.getBedNo() : "",
                "moveInDate", a.getMoveInDate() != null ? a.getMoveInDate().toString() : ""
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("data", result));
    }

    public record AllocateRequest(String studentId, String roomId, String bedNo) {}

    @PostMapping("/allocate")
    public ResponseEntity<?> allocate(@RequestBody AllocateRequest req) {
        Long studentId = Long.valueOf(req.studentId());
        Long roomId = Long.valueOf(req.roomId());
        String bedNo = req.bedNo();

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "学生不存在"));
        }

        DormRoom room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "房间不存在"));
        }

        if (allocationRepository.findByStudent_Id(studentId).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "该学生已分配宿舍"));
        }

        List<DormAllocation> roomAllocs = allocationRepository.findByRoom_Id(roomId);
        if (room.getTotalBeds() != null && roomAllocs.size() >= room.getTotalBeds()) {
            return ResponseEntity.badRequest().body(Map.of("message", "该房间已满"));
        }

        DormAllocation allocation = new DormAllocation();
        allocation.setStudent(student);
        allocation.setRoom(room);
        allocation.setBedNo(bedNo);
        allocation.setMoveInDate(LocalDate.now());
        allocationRepository.save(allocation);

        return ResponseEntity.ok(Map.of("message", "分配成功", "id", allocation.getId()));
    }

    @DeleteMapping("/allocate/{id}")
    public ResponseEntity<?> deallocate(@PathVariable Long id) {
        if (!allocationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        allocationRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "已取消分配"));
    }
}
