package com.clownstore.cart.kafka;

import com.clownstore.cart.repository.CartRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final String STOCK_RESERVED= "stock-reserved";
    private final CartRepository cartRepository;

    public Consumer(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @KafkaListener(topics = STOCK_RESERVED, groupId = "cart-group")
    public void cleanCartListener(StockReservedEvent event) {
        cartRepository.deleteCartByUserId(event.userId());
    }
}
