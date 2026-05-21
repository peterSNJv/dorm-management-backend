package com.dorm.intelligent;

import java.util.List;

/**
 * 资源动态优化调度服务（床位分配、调宿建议等）。
 */
public interface ResourceScheduleService {

    /**
     * 根据楼栋、性别、空床位等条件推荐可分配房间，支持自动化分配流程。
     */
    List<RoomRecommendation> recommendRooms(String building, String gender, int requiredBeds);

    /**
     * 调宿建议：基于空置率、均衡性等给出可调整方案（可选实现）。
     */
    List<TransferSuggestion> suggestTransfers(String building);

    record RoomRecommendation(String roomId, String roomNo, int availableBeds, double score) {}
    record TransferSuggestion(String fromRoom, String toRoom, String reason) {}
}
