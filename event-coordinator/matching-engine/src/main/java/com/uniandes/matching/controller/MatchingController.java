package com.uniandes.matching.controller;

import com.uniandes.matching.domain.model.Order;
import com.uniandes.matching.domain.model.OrderType;
import com.uniandes.matching.domain.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/ordenes")
public class MatchingController {

    private final OrderService orderService;

    public MatchingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> recibirOrden(@RequestBody OrderRequest request) {
        try {
            // 1. Convertir DTO a Modelo de Dominio
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setSymbol(request.getSymbol());
            order.setQuantity(request.getQuantity());
            order.setPrice(request.getPrice());
            order.setType(OrderType.valueOf(request.getType())); // BUY o SALE
            order.setUserId(request.getUserId());
            order.setTimestamp(LocalDateTime.now());

            // 2. Invocar al motor de emparejamiento (que ahora tiene la trampa para notificar a Go)
            orderService.createOrder(order);
            
            return ResponseEntity.ok("Orden recibida y procesando: " + order.getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error procesando orden: " + e.getMessage());
        }
    }
}