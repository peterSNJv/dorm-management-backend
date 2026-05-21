package com.dorm.repository;

import com.dorm.domain.entity.DormRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormRoomRepository extends JpaRepository<DormRoom, Long> {

    Optional<DormRoom> findByBuildingAndRoomNo(String building, String roomNo);
}
