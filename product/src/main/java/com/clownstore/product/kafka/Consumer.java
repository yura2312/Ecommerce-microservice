package com.clownstore.product.kafka;

import com.clownstore.product.exception.ProductNotFoundException;
import com.clownstore.product.model.Product;
import com.clownstore.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class Consumer {

    private static final String ORDER_CREATED = "order-created";
    private static final String STOCK_RESERVED = "stock-reserved";
    private static final String STOCK_RESERVATION_FAILED = "stock-reservation-failed";
    private ProductRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Consumer(ProductRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = ORDER_CREATED, groupId = "product-group")
    public void stockReservationListener(OrderCreatedEvent event) {
        log.info("Processing order created event for orderId: {}, userId: {}", event.id(), event.userId());

        Map<String, Product> productsById = new HashMap<>();
        List<FailedItemPayload> insufficientItems = new ArrayList<>();

        // Check stock availability for all items
        for (OrderItemPayload item : event.items()) {
            Product product = repository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product with id " + item.productId() + " not found"));
            productsById.put(item.productId(), product);

            if (product.getStock() < item.quantity()) {
                log.warn("Insufficient stock for product: {}, requested: {}, available: {}",
                        item.productId(), item.quantity(), product.getStock());
                insufficientItems.add(mapFailedItem(item, product));
            }
        }

        // If any items have insufficient stock, publish failure event
        if (!insufficientItems.isEmpty()) {
            log.info("Stock reservation failed for order: {}", event.id());
            StockReservationFailedEvent failureEvent = mapFailureEvent(event, insufficientItems);
            kafkaTemplate.send(STOCK_RESERVATION_FAILED, event.userId(), failureEvent);
            return;
        }

        // Deduct stock from all products
        List<ReservedItemPayload> reservedItems = new ArrayList<>();
        for (OrderItemPayload itemPayload : event.items()) {
            Product product = productsById.get(itemPayload.productId());

            product.setStock(product.getStock() - itemPayload.quantity());
            repository.save(product);

            reservedItems.add(mapReservedItem(itemPayload, product));
            log.info("Stock deducted for product: {}, new stock: {}", product.getId(), product.getStock());
        }

        // Publish stock reserved event
        log.info("Stock successfully reserved for order: {}", event.id());
        StockReservedEvent reservedEvent = mapReservedEvent(event, reservedItems);
        kafkaTemplate.send(STOCK_RESERVED, event.userId(), reservedEvent);
    }

    private FailedItemPayload mapFailedItem(OrderItemPayload item, Product product) {
        return FailedItemPayload.builder()
                .productId(item.productId())
                .requestedQuantity(item.quantity())
                .availableStock(product.getStock())
                .build();
    }

    private StockReservationFailedEvent mapFailureEvent(
            OrderCreatedEvent event,
            List<FailedItemPayload> insufficientItems
    ) {
        return StockReservationFailedEvent.builder()
                .orderId(event.id())
                .userId(event.userId())
                .insufficientItems(insufficientItems)
                .build();
    }

    private ReservedItemPayload mapReservedItem(OrderItemPayload item, Product product) {
        return ReservedItemPayload.builder()
                .productId(product.getId())
                .quantity(item.quantity())
                .build();
    }

    private StockReservedEvent mapReservedEvent(
            OrderCreatedEvent event,
            List<ReservedItemPayload> reservedItems
    ) {
        return StockReservedEvent.builder()
                .orderId(event.id())
                .userId(event.userId())
                .items(reservedItems)
                .build();
    }
}
