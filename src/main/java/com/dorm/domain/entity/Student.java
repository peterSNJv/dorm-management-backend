package com.dorm.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 学生实体（核心域模型，与教务/认证系统通过集成层同步）。
 */
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 学号（来自统一身份认证或教务系统） */
    @Column(unique = true, nullable = false, length = 32)
    private String studentNo;

    @Column(length = 64)
    private String name;

    /** 班级（来自教务系统集成） */
    @Column(length = 64)
    private String className;

    @Column(length = 32)
    private String phone;

    /** 登录密码（BCrypt 加密存储） */
    @Column(length = 100)
    private String password;

    /** 敏感信息需加密存储，此处仅示例字段名 */
    @Column(length = 256)
    private String idCardEncrypted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getIdCardEncrypted() { return idCardEncrypted; }
    public void setIdCardEncrypted(String idCardEncrypted) { this.idCardEncrypted = idCardEncrypted; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
