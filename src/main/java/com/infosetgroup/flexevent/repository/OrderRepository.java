package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.AppAccount;
import com.infosetgroup.flexevent.entity.Item;
import com.infosetgroup.flexevent.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(AppAccount customer);
    Order findByCode(String code);
    Order findByItemAndPaidAndBookingDateEquals(Item item, boolean paid, LocalDateTime bookingDate);
}
