package com.clownstore.order.repository;

import com.clownstore.order.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional
    void deleteByUserId(String userId);

}
