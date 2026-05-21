package com.dorm.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统集成配置：统一身份认证、财务、教务、安防等外部系统端点与开关。
 * 通过配置启用/禁用各集成，避免与外部系统强耦合，便于扩展与替换。
 */
@Configuration
public class IntegrationConfig {

    @Bean
    @ConfigurationProperties(prefix = "dorm.integration.sso")
    public SsoProperties ssoProperties() {
        return new SsoProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "dorm.integration.finance")
    public FinanceProperties financeProperties() {
        return new FinanceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "dorm.integration.academic")
    public AcademicProperties academicProperties() {
        return new AcademicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "dorm.integration.security-monitor")
    public SecurityMonitorProperties securityMonitorProperties() {
        return new SecurityMonitorProperties();
    }

    public static class SsoProperties {
        private boolean enabled = false;
        private String baseUrl = "";
        private String tokenValidatePath = "/api/auth/validate";
        private String userInfoPath = "/api/user/info";

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public String getTokenValidatePath() { return tokenValidatePath; }
        public void setTokenValidatePath(String path) { this.tokenValidatePath = path; }
        public String getUserInfoPath() { return userInfoPath; }
        public void setUserInfoPath(String path) { this.userInfoPath = path; }
    }

    public static class FinanceProperties {
        private boolean enabled = false;
        private String baseUrl = "";
        private String feeSyncPath = "/api/fee/sync";

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public String getFeeSyncPath() { return feeSyncPath; }
        public void setFeeSyncPath(String path) { this.feeSyncPath = path; }
    }

    public static class AcademicProperties {
        private boolean enabled = false;
        private String baseUrl = "";
        private String studentClassPath = "/api/student/class";

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public String getStudentClassPath() { return studentClassPath; }
        public void setStudentClassPath(String path) { this.studentClassPath = path; }
    }

    public static class SecurityMonitorProperties {
        private boolean enabled = false;
        private String baseUrl = "";
        private String alarmCallbackPath = "/api/alarm/callback";

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public String getAlarmCallbackPath() { return alarmCallbackPath; }
        public void setAlarmCallbackPath(String path) { this.alarmCallbackPath = path; }
    }
}
