package com.barber.systembarber.repository;

import com.barber.systembarber.model.Appointment;
import com.barber.systembarber.model.Cliente;
import com.barber.systembarber.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.barber = :barber AND a.barber.role = 'BARBER'")
    List<Appointment> findByBarber(@Param("barber") UserModel barber);

    @Query("SELECT a FROM Appointment a WHERE a.barber = :barber " +
            "AND a.appointmentStart < :appointmentEnd AND a.appointmentEnd > :appointmentStart")
    List<Appointment> findConflictingAppointments(@Param("barber") UserModel barber,
                                                  @Param("appointmentStart") LocalDateTime appointmentStart,
                                                  @Param("appointmentEnd") LocalDateTime appointmentEnd);

    @Query("SELECT a FROM Appointment a WHERE a.client = :client")
    List<Appointment> findByClient(@Param("client") Cliente client);
}