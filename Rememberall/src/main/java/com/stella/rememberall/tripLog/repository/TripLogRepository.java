package com.stella.rememberall.tripLog.repository;

import com.stella.rememberall.tripLog.entity.TripLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripLogRepository extends JpaRepository<TripLog, Long> {
    List<TripLog> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
