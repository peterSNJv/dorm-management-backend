package com.dorm.intelligent;

import java.time.Instant;
import java.util.List;

/**
 * 安全风险智能预警服务。
 * 对接门禁、用电、安防等数据，产生预警事件供管理端处置。
 */
public interface RiskWarningService {

    /**
     * 获取未处置或近期预警列表。
     */
    List<RiskWarning> listWarnings(boolean unhandledOnly, Instant since);

    /**
     * 标记预警已处置（与安防/门禁系统联动时可回调对方）。
     */
    void markHandled(String warningId, String handlerId, String remark);

    record RiskWarning(String id, String type, String building, String roomNo, String description, Instant at, boolean handled) {}
}
