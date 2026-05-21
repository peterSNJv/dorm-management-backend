package com.dorm.controller;

import com.dorm.domain.entity.Feedback;
import com.dorm.repository.StudentRepository;
import com.dorm.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final StudentRepository studentRepository;

    public FeedbackController(FeedbackService feedbackService, StudentRepository studentRepository) {
        this.feedbackService = feedbackService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body,
                                   @AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        
        Feedback feedback = new Feedback();
        feedback.setStudentNo(studentNo);
        studentRepository.findByStudentNo(studentNo).ifPresent(s -> feedback.setStudentName(s.getName()));
        
        if (body.get("type") != null) feedback.setType(body.get("type").toString());
        if (body.get("content") != null) feedback.setContent(body.get("content").toString());
        if (body.get("contact") != null) feedback.setContact(body.get("contact").toString());
        
        Feedback saved = feedbackService.create(feedback);
        return ResponseEntity.ok(Map.of("message", "提交成功", "id", saved.getId()));
    }

    @GetMapping("/feedback/list")
    public ResponseEntity<?> list(@AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).body(Map.of("message", "未登录"));
        List<Feedback> list = feedbackService.findByStudentNo(studentNo);
        return ResponseEntity.ok(Map.of("data", list, "list", list));
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Feedback feedback = feedbackService.findById(id);
        if (feedback == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("data", feedback));
    }
}