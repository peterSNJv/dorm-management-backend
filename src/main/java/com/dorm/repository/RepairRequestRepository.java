package com.dorm.repository;

import com.dorm.domain.entity.RepairRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long> {
    List<RepairRequest> findByStudentNoOrderByCreatedAtDesc(String studentNo);
}