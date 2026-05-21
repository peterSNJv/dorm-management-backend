package com.dorm.security;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * 安全审计日志：敏感操作（登录、访问住宿/门禁/用电数据等）留痕。
 */
@Entity
@Table(name = "audit_log", indexes = @Index(columnList = "userId,action,createdAt"))
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String action;   // LOGIN, VIEW_DORM, VIEW_FEE, EXPORT, ...
    private String resource; // dorm, fee, access, ...
    private String result;   // SUCCESS, DENIED
    private String ip;
    private String userAgent;

    private Instant createdAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
