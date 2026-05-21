package com.dorm.security;

import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository repository;

    public AuditServiceImpl(AuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void log(String userId, String action, String resource, String result, String ip, String userAgent) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setResource(resource);
        log.setResult(result);
        log.setIp(ip);
        log.setUserAgent(userAgent);
        repository.save(log);
    }
}
