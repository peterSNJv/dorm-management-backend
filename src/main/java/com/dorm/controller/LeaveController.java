package com.dorm.controller;

import com.dorm.domain.entity.LeaveRequest;
import com.dorm.repository.StudentRepository;
import com.dorm.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LeaveController {

    private final LeaveService leaveService;
    private final StudentRepository studentRepository;

    public LeaveController(LeaveService leaveService, StudentRepository studentRepository) {
        this.leaveService = leaveService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/leave/apply")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body,
                                   @AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        
        LeaveRequest request = new LeaveRequest();
        request.setStudentNo(studentNo);
        studentRepository.findByStudentNo(studentNo).ifPresent(s -> request.setStudentName(s.getName()));
        
        if (body.get("typeId") != null) request.setTypeId(Integer.valueOf(body.get("typeId").toString()));
        if (body.get("typeName") != null) request.setTypeName(body.get("typeName").toString());
        if (body.get("startDate") != null) request.setStartDate(body.get("startDate").toString());
        if (body.get("endDate") != null) request.setEndDate(body.get("endDate").toString());
        if (body.get("reason") != null) request.setReason(body.get("reason").toString());
        if (body.get("contact") != null) request.setContact(body.get("contact").toString());
        
        LeaveRequest saved = leaveService.create(request);
        return ResponseEntity.ok(Map.of("message", "提交成功", "id", saved.getId()));
    }

    @GetMapping("/leave/list")
    public ResponseEntity<?> list(@AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        List<LeaveRequest> list = leaveService.findByStudentNo(studentNo);
        return ResponseEntity.ok(Map.of("data", list, "list", list));
    }

    @GetMapping("/leave/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        LeaveRequest request = leaveService.findById(id);
        if (request == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("data", request));
    }
}