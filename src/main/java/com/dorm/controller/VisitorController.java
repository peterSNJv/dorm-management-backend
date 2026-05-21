package com.dorm.controller;

import com.dorm.domain.entity.VisitorRecord;
import com.dorm.repository.StudentRepository;
import com.dorm.service.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VisitorController {

    private final VisitorService visitorService;
    private final StudentRepository studentRepository;

    public VisitorController(VisitorService visitorService, StudentRepository studentRepository) {
        this.visitorService = visitorService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/visitor/apply")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body,
                                   @AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        
        VisitorRecord record = new VisitorRecord();
        record.setStudentNo(studentNo);
        studentRepository.findByStudentNo(studentNo).ifPresent(s -> record.setStudentName(s.getName()));
        
        if (body.get("visitorName") != null) record.setVisitorName(body.get("visitorName").toString());
        if (body.get("visitorPhone") != null) record.setVisitorPhone(body.get("visitorPhone").toString());
        if (body.get("reason") != null) record.setReason(body.get("reason").toString());
        if (body.get("visitTime") != null) record.setVisitTime(body.get("visitTime").toString());
        
        VisitorRecord saved = visitorService.create(record);
        return ResponseEntity.ok(Map.of("message", "登记成功", "id", saved.getId()));
    }

    @GetMapping("/visitor/records")
    public ResponseEntity<?> records(@AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        List<VisitorRecord> list = visitorService.findByStudentNo(studentNo);
        return ResponseEntity.ok(Map.of("data", list, "list", list));
    }

    @GetMapping("/visitor/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        VisitorRecord record = visitorService.findById(id);
        if (record == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("data", record));
    }
}