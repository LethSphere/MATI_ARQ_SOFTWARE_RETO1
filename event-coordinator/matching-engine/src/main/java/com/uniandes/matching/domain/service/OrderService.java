package com.uniandes.matching.domain.service;

import com.uniandes.matching.domain.model.Match;
import com.uniandes.matching.domain.model.Order;
import com.uniandes.matching.domain.model.OrderType;
import com.uniandes.matching.domain.repository.BuyOrderRepository;
import com.uniandes.matching.domain.repository.SaleOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {
    private final SaleOrderRepository saleOrderRepository;
    private final BuyOrderRepository buyOrderRepository;
    private final NotificationService notificationService; // <--- 1. Inyectamos el servicio de notificación

    public OrderService(SaleOrderRepository saleOrderRepository,
                        BuyOrderRepository buyOrderRepository,
                        NotificationService notificationService) {
        this.saleOrderRepository = saleOrderRepository;
        this.buyOrderRepository = buyOrderRepository;
        this.notificationService = notificationService;
    }

    public Order createOrder(Order order) {
        order.initialize();

        // ⚠️ SOLO PARA TESTING - Simula BD lenta
//        try {
//            Thread.sleep(200); // 200ms delay
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        // ⚠️ FIN TESTING

        log.info("Creating order: {} - Type: {} - Symbol: {}", order.getId(), order.getType(), order.getSymbol());

        Order savedOrder;
        if (order.getType() == OrderType.SALE) {
            savedOrder = saleOrderRepository.save(order);
        } else {
            savedOrder = buyOrderRepository.save(order);
        }

        // ⚠️ TRAMPA PARA PROBAR LA CONEXIÓN A GO ⚠️
        // Como no hay motor de emparejamiento real aún, creamos un Match ficticio
        // apenas llega la orden, solo para ver si el mensaje llega a Go.
        Match fakeMatch = Match.create(savedOrder, savedOrder, 10); 
        // (Usamos la misma orden dos veces solo para llenar los datos)
        
        notificationService.sendMatchNotification(fakeMatch);
        // ⚠️ FIN TRAMPA ⚠️

        return savedOrder;
    }
}
