package com.barber.systembarber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Cliente client;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private UserModel barber;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Order barberOrderService;

    @Column(nullable = false)
    private LocalDateTime appointmentStart;

    @Column(nullable = false)
    private LocalDateTime appointmentEnd;

    private String status;
}
