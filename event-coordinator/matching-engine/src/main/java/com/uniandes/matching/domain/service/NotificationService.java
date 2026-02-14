package com.uniandes.matching.domain.service;

import com.uniandes.matching.domain.model.Match;
import com.uniandes.matching.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void broadcastNewOrder(Order order) {
        log.info("Broadcasting new {} order: {} - Symbol: {} - Price: {} - Qty: {}",
                order.getType(),
                order.getId(),
                order.getSymbol(),
                order.getPrice(),
                order.getQuantity());

        // TODO: Implementar WebSocket broadcast cuando esté listo
    }
}