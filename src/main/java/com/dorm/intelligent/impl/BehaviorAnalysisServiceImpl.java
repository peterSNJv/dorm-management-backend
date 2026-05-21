package com.dorm.intelligent.impl;

import com.dorm.intelligent.BehaviorAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 住宿行为分析实现（可接入门禁、用电等数据源做统计与规则引擎）。
 */
@Service
public class BehaviorAnalysisServiceImpl implements BehaviorAnalysisService {

    @Override
    public List<BehaviorSummary> summarizeLateOrAbsent(String building, LocalDate start, LocalDate end) {
        // TODO: 从门禁/请假数据聚合晚归、未归记录
        return Collections.emptyList();
    }

    @Override
    public List<BehaviorSummary> summarizeElectricAnomaly(String building, LocalDate start, LocalDate end) {
        // TODO: 从用电数据识别异常
        return Collections.emptyList();
    }
}
