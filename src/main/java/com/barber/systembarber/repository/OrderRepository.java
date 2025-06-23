package com.barber.systembarber.repository;

import com.barber.systembarber.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
