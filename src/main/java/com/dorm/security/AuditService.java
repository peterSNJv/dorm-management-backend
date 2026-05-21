package com.dorm.security;

/**
 * 审计服务：记录敏感操作，便于事后追溯与合规。
 */
public interface AuditService {

    void log(String userId, String action, String resource, String result, String ip, String userAgent);
}
