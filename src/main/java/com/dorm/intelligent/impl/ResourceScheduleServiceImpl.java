package com.dorm.intelligent.impl;

import com.dorm.intelligent.ResourceScheduleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 资源动态调度实现（基于空床位与规则推荐房间）。
 */
@Service
public class ResourceScheduleServiceImpl implements ResourceScheduleService {

    @Override
    public List<RoomRecommendation> recommendRooms(String building, String gender, int requiredBeds) {
        // TODO: 查询空床位、性别楼栋等规则
        return Collections.emptyList();
    }

    @Override
    public List<TransferSuggestion> suggestTransfers(String building) {
        // TODO: 空置率、均衡性分析
        return Collections.emptyList();
    }
}
