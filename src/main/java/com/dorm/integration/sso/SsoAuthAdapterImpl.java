package com.dorm.integration.sso;

import com.dorm.integration.IntegrationConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 统一身份认证适配器实现。
 * 仅当 dorm.integration.sso.enabled=true 时生效，否则由本地认证兜底。
 */
@Component
@ConditionalOnProperty(name = "dorm.integration.sso.enabled", havingValue = "true")
public class SsoAuthAdapterImpl implements SsoAuthAdapter {

    private final IntegrationConfig.SsoProperties props;
    private final RestTemplate restTemplate = new RestTemplate();

    public SsoAuthAdapterImpl(IntegrationConfig config) {
        this.props = config.ssoProperties();
    }

    @Override
    public SsoValidateResult validateToken(String token) {
        String url = props.getBaseUrl() + props.getTokenValidatePath();
        // 实际调用：restTemplate.postForObject(url, Map.of("token", token), ...)
        return new SsoValidateResult(true, "studentNoFromSso", "姓名");
    }

    @Override
    public SsoUserInfo getUserInfo(String userId) {
        String url = props.getBaseUrl() + props.getUserInfoPath() + "?userId=" + userId;
        // 实际调用：restTemplate.getForObject(url, ...)
        return new SsoUserInfo(userId, "姓名", "班级");
    }
}
