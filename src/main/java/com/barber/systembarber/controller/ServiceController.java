package com.barber.systembarber.controller;

import com.barber.systembarber.model.Order;
import com.barber.systembarber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public Order createOrderService(@RequestBody Order service) {
        return orderService.createService(service);
    }

    @GetMapping()
    public Order getServiceById(@RequestParam Long id) {
        return orderService.getServiceById(id);
    }

    @GetMapping("/all-services")
    public ResponseEntity<List<Order>> getServices() {
        try {
            List<Order> services = orderService.getAllServices();

            return ResponseEntity.ok(services);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PatchMapping("/update-service")
    public Order editServices(@RequestParam Long id, @RequestBody Order updateService) {
        try {
            return orderService.updateService(id, updateService);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}