package com.dorm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 费用查询接口，供小程序联调。
 */
@RestController
@RequestMapping("/api")
public class FeeController {

    @GetMapping("/fee/my")
    public ResponseEntity<?> myBills(@AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).build();
        // 联调阶段返回示例数据；后续可改为从数据库或财务系统同步
        List<Map<String, Object>> list = List.of(
            Map.<String, Object>of("id", "1", "name", "住宿费", "amount", "1200", "month", "2025-02", "paid", false, "remark", "本学期"),
            Map.<String, Object>of("id", "2", "name", "水电费", "amount", "86.50", "month", "2025-02", "paid", true, "remark", "已缴")
        );
        return ResponseEntity.ok(Map.of("data", list, "list", list));
    }

    @GetMapping("/fee/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable String id, @AuthenticationPrincipal String studentNo) {
        if (studentNo == null) return ResponseEntity.status(401).build();
        Map<String, Object> bill = Map.of(
            "id", id,
            "name", "住宿费",
            "amount", "1200",
            "month", "2025-02",
            "paid", false,
            "remark", "本学期"
        );
        return ResponseEntity.ok(Map.of("data", bill));
    }
}
