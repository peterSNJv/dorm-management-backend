package com.dorm.intelligent.impl;

import com.dorm.intelligent.RiskWarningService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * 安全风险预警实现（可对接安防监控系统回调与门禁事件）。
 */
@Service
public class RiskWarningServiceImpl implements RiskWarningService {

    @Override
    public List<RiskWarning> listWarnings(boolean unhandledOnly, Instant since) {
        // TODO: 从事件表或安防回调数据查询
        return Collections.emptyList();
    }

    @Override
    public void markHandled(String warningId, String handlerId, String remark) {
        // TODO: 更新状态并可选回调安防系统
    }
}
