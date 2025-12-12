package com.clownstore.order.repository;

import com.clownstore.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<OrderItem, Long> {
}
