package com.dorm.controller;

import com.dorm.domain.entity.Feedback;
import com.dorm.domain.entity.LeaveRequest;
import com.dorm.domain.entity.RepairRequest;
import com.dorm.domain.entity.VisitorRecord;
import com.dorm.service.FeedbackService;
import com.dorm.service.LeaveService;
import com.dorm.service.RepairService;
import com.dorm.service.VisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminApprovalController {

    private final LeaveService leaveService;
    private final RepairService repairService;
    private final VisitorService visitorService;
    private final FeedbackService feedbackService;

    public AdminApprovalController(LeaveService leaveService, RepairService repairService,
                                   VisitorService visitorService, FeedbackService feedbackService) {
        this.leaveService = leaveService;
        this.repairService = repairService;
        this.visitorService = visitorService;
        this.feedbackService = feedbackService;
    }

    // ==================== 请假审批 ====================

    @GetMapping("/leave")
    public ResponseEntity<?> listLeaveRequests() {
        List<LeaveRequest> list = leaveService.findAll();
        return ResponseEntity.ok(Map.of("data", list));
    }

    @PutMapping("/leave/{id}/approve")
    public ResponseEntity<?> approveLeave(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        String note = body != null ? body.get("note") : null;
        LeaveRequest r = leaveService.updateStatus(id, "APPROVED", note);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "已批准"));
    }

    @PutMapping("/leave/{id}/reject")
    public ResponseEntity<?> rejectLeave(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        String note = body != null ? body.get("note") : null;
        LeaveRequest r = leaveService.updateStatus(id, "REJECTED", note);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "已拒绝"));
    }

    // ==================== 报修管理 ====================

    @GetMapping("/repair")
    public ResponseEntity<?> listRepairRequests() {
        List<RepairRequest> list = repairService.findAll();
        return ResponseEntity.ok(Map.of("data", list));
    }

    @PutMapping("/repair/{id}/status")
    public ResponseEntity<?> updateRepairStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        String note = body.get("note");
        RepairRequest r = repairService.updateStatus(id, status, note);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "状态已更新"));
    }

    // ==================== 访客审批 ====================

    @GetMapping("/visitor")
    public ResponseEntity<?> listVisitorRecords() {
        List<VisitorRecord> list = visitorService.findAll();
        return ResponseEntity.ok(Map.of("data", list));
    }

    @PutMapping("/visitor/{id}/approve")
    public ResponseEntity<?> approveVisitor(@PathVariable Long id) {
        VisitorRecord r = visitorService.updateStatus(id, "APPROVED");
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "已批准"));
    }

    @PutMapping("/visitor/{id}/reject")
    public ResponseEntity<?> rejectVisitor(@PathVariable Long id) {
        VisitorRecord r = visitorService.updateStatus(id, "REJECTED");
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "已拒绝"));
    }

    // ==================== 反馈管理 ====================

    @GetMapping("/feedback")
    public ResponseEntity<?> listFeedback() {
        List<Feedback> list = feedbackService.findAll();
        return ResponseEntity.ok(Map.of("data", list));
    }

    @PutMapping("/feedback/{id}/reply")
    public ResponseEntity<?> replyFeedback(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String reply = body.get("reply");
        Feedback f = feedbackService.reply(id, reply);
        if (f == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "已回复"));
    }
}
