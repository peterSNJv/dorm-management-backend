package com.dorm.repository;

import com.dorm.domain.entity.VisitorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRecordRepository extends JpaRepository<VisitorRecord, Long> {
    List<VisitorRecord> findByStudentNoOrderByCreatedAtDesc(String studentNo);
}