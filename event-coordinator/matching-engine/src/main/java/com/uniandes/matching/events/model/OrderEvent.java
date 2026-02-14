package com.uniandes.matching.events.model;

import com.uniandes.matching.domain.model.Order;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private EventType type;
    private Order order;
    private LocalDateTime timestamp;
    private String errorMessage;

    public static OrderEvent of(EventType type, Order order) {
        return OrderEvent.builder()
                .type(type)
                .order(order)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static OrderEvent error(EventType type, Order order, String errorMessage) {
        return OrderEvent.builder()
                .type(type)
                .order(order)
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }
}