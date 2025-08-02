package com.barber.systembarber.service;

import com.barber.systembarber.model.Order;
import com.barber.systembarber.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Service
public class OrderService {
    @Autowired
    private OrderRepository serviceRepository;

    public Order createService(Order service) {
        try {
            return serviceRepository.save(service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Order getServiceById(Long id) {
        try {
            return serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Order> getAllServices() {
        try {
            return serviceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Order updateService(Long id, Order updateService) {
        try {
            Order verify_order = serviceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Serviço com ID " + id + " não encontrado."));;

                verify_order.setName_services(updateService.getName_services());
                verify_order.setDescription_services(updateService.getDescription_services());
                verify_order.setPrice_services(updateService.getPrice_services());
                verify_order.setDurationInMinutes(updateService.getDurationInMinutes());

            return serviceRepository.save(verify_order);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
