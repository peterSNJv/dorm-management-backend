package com.dorm.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 访客记录实体
 */
@Entity
@Table(name = "visitor_record")
public class VisitorRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String studentNo;

    @Column(length = 64)
    private String studentName;

    @Column(nullable = false, length = 32)
    private String visitorName;

    @Column(length = 32)
    private String visitorPhone;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(length = 64)
    private String visitTime;

    @Column(length = 16)
    private String status; // PENDING, APPROVED, REJECTED, VISITED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime visitedAt;
    private String note;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }
    public String getVisitorPhone() { return visitorPhone; }
    public void setVisitorPhone(String visitorPhone) { this.visitorPhone = visitorPhone; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getVisitTime() { return visitTime; }
    public void setVisitTime(String visitTime) { this.visitTime = visitTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getVisitedAt() { return visitedAt; }
    public void setVisitedAt(LocalDateTime visitedAt) { this.visitedAt = visitedAt; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}