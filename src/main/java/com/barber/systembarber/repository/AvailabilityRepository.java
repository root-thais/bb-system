package com.barber.systembarber.repository;

import com.barber.systembarber.model.AvailabilityModel;
import com.barber.systembarber.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityModel, Long> {

    @Query("SELECT a FROM AvailabilityModel a WHERE a.user = :user " +
            "AND a.user.role = 'BARBER' " +
            "AND a.dateTimeStart <= :startTime " +
            "AND a.dateTimeEnd >= :endTime " +
            "AND a.available = true")
    Optional<AvailabilityModel> findAvailableSlot(@Param("user") UserModel user,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);
}