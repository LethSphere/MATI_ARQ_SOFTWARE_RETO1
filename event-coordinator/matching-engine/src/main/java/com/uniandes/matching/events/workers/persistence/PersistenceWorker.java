package com.uniandes.matching.events.workers.persistence;

import com.uniandes.matching.domain.model.Order;
import com.uniandes.matching.domain.service.OrderService;
import com.uniandes.matching.events.bus.EventBus;
import com.uniandes.matching.events.model.EventType;
import com.uniandes.matching.events.model.OrderEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class PersistenceWorker implements Runnable {

    private final BlockingQueue<OrderEvent> queue;
    private final OrderService orderService;
    private final EventBus eventBus;
    private final String workerName;

    public PersistenceWorker(BlockingQueue<OrderEvent> queue,
                             OrderService orderService,
                             EventBus eventBus,
                             String workerName) {
        this.queue = queue;
        this.orderService = orderService;
        this.eventBus = eventBus;
        this.workerName = workerName;
    }

    @Override
    public void run() {
        log.info("{} started", workerName);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                OrderEvent event = queue.take();

                log.debug("{} processing order: {}", workerName, event.getOrder().getId());

                long startTime = System.currentTimeMillis();

                orderService.createOrder(event.getOrder());

                long elapsed = System.currentTimeMillis() - startTime;

                log.debug("{} persisted order {} in {}ms",
                        workerName, event.getOrder().getId(), elapsed);

                eventBus.publish(OrderEvent.of(EventType.ORDER_PERSISTED, event.getOrder()));

            } catch (InterruptedException e) {
                log.warn("{} interrupted, shutting down", workerName);
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("{} error processing order: {}", workerName, e.getMessage(), e);
            }
        }

        log.info("{} stopped", workerName);
    }
}