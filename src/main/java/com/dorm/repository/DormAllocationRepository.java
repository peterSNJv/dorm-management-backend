package com.dorm.repository;

import com.dorm.domain.entity.DormAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormAllocationRepository extends JpaRepository<DormAllocation, Long> {

    Optional<DormAllocation> findByStudent_Id(Long studentId);
}
