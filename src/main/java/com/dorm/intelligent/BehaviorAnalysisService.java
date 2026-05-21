package com.dorm.intelligent;

import java.time.LocalDate;
import java.util.List;

/**
 * 住宿行为分析服务（智能化模块）。
 * 基于门禁、用电、请假等数据做统计分析，为管理决策与风险预警提供输入。
 */
public interface BehaviorAnalysisService {

    /**
     * 某楼栋/某时间段晚归、未归统计，用于安全风险研判。
     */
    List<BehaviorSummary> summarizeLateOrAbsent(String building, LocalDate start, LocalDate end);

    /**
     * 某宿舍用电异常（突增/长期空置）等行为摘要。
     */
    List<BehaviorSummary> summarizeElectricAnomaly(String building, LocalDate start, LocalDate end);

    record BehaviorSummary(String roomNo, String type, String description, double score) {}
}
