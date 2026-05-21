package com.dorm.integration.sso;

import com.dorm.integration.IntegrationConfig;

/**
 * 统一身份认证（SSO）适配器接口。
 * 实现类通过 HTTP 调用学校 SSO 校验 token / 拉取用户信息，与业务层解耦。
 */
public interface SsoAuthAdapter {

    /**
     * 校验外部 token，返回本校用户标识（如学号）。
     */
    SsoValidateResult validateToken(String token);

    /**
     * 根据学号/工号拉取用户基本信息（姓名、班级等），用于与教务数据合并。
     */
    SsoUserInfo getUserInfo(String userId);

    record SsoValidateResult(boolean valid, String userId, String name) {}
    record SsoUserInfo(String userId, String name, String className) {}
}
