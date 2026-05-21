package com.dorm.controller;

import com.dorm.domain.entity.RepairRequest;
import com.dorm.repository.StudentRepository;
import com.dorm.service.RepairService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RepairController {

    private final RepairService repairService;
    private final StudentRepository studentRepository;

    public RepairController(RepairService repairService, StudentRepository studentRepository) {
        this.repairService = repairService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/repair")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body, 
                                   @AuthenticationPrincipal String studentNo,
                                   HttpServletRequest request) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        
        RepairRequest repair = new RepairRequest();
        repair.setStudentNo(studentNo);
        studentRepository.findByStudentNo(studentNo).ifPresent(s -> repair.setStudentName(s.getName()));
        
        if (body.get("typeId") != null) repair.setTypeId(Integer.valueOf(body.get("typeId").toString()));
        if (body.get("typeName") != null) repair.setTypeName(body.get("typeName").toString());
        if (body.get("description") != null) repair.setDescription(body.get("description").toString());
        if (body.get("contactPhone") != null) repair.setContactPhone(body.get("contactPhone").toString());
        
        RepairRequest saved = repairService.create(repair);
        return ResponseEntity.ok(Map.of("message", "提交成功", "id", saved.getId()));
    }

    @GetMapping("/repair/list")
    public ResponseEntity<?> list(@AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        List<RepairRequest> list = repairService.findByStudentNo(studentNo);
        return ResponseEntity.ok(Map.of("data", list, "list", list));
    }

    @GetMapping("/repair/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        RepairRequest repair = repairService.findById(id);
        if (repair == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("data", repair));
    }
}