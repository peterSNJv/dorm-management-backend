package com.dorm.controller;

import com.dorm.repository.DormAllocationRepository;
import com.dorm.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * 学生与宿舍信息接口（访问控制与审计在 Service/Filter 层统一处理）。
 */
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository studentRepository;
    private final DormAllocationRepository allocationRepository;

    public StudentController(StudentRepository studentRepository, DormAllocationRepository allocationRepository) {
        this.studentRepository = studentRepository;
        this.allocationRepository = allocationRepository;
    }

    @GetMapping("/student/dorm")
    public ResponseEntity<?> myDorm(@AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).build();
        Optional<com.dorm.domain.entity.Student> studentOpt = studentRepository.findByStudentNo(studentNo);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.ok(defaultDormMap());
        }
        Optional<com.dorm.domain.entity.DormAllocation> allocOpt = allocationRepository.findByStudent_Id(studentOpt.get().getId());
        if (allocOpt.isEmpty()) {
            return ResponseEntity.ok(defaultDormMap());
        }
        com.dorm.domain.entity.DormAllocation a = allocOpt.get();
        com.dorm.domain.entity.DormRoom room = a.getRoom();
        Map<String, Object> map = Map.of(
            "building", room.getBuilding(),
            "floor", room.getFloor() != null ? room.getFloor() : "-",
            "roomNo", room.getRoomNo(),
            "bedNo", a.getBedNo() != null ? a.getBedNo() : "",
            "moveInDate", a.getMoveInDate() != null ? a.getMoveInDate().toString() : ""
        );
        return ResponseEntity.ok(map);
    }

    private static Map<String, String> defaultDormMap() {
        return Map.of(
            "building", "-", "floor", "-", "roomNo", "-", "bedNo", "-", "moveInDate", ""
        );
    }
}
