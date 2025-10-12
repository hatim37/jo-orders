package com.ecom.orders.controller;

import com.ecom.orders.config.UsersOrderInitializer;
import com.ecom.orders.entity.Order;
import com.ecom.orders.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class MicroServiceController {

    private final OrderService orderService;
    private final UsersOrderInitializer usersOrderInitializer;

    public MicroServiceController(OrderService orderService, UsersOrderInitializer usersOrderInitializer) {
        this.orderService = orderService;
        this.usersOrderInitializer = usersOrderInitializer;
    }

    @PostMapping(path = "/_internal/order-save")
    public void save(@RequestBody Order order) {
        this.orderService.updateOrderTotal(order);
    }

    @PostMapping(path = "/_internal/order-user")
    public void orderSave(@RequestBody Order order) {
        this.orderService.newOrder(order);
    }

    @PostMapping("/_internal/orderFindUserOrderStatus")
    public Order findByUserIdAndOrderStatus(@RequestBody Map<String, String> mapOrder){
        return this.orderService.findByUserIdAndOrderStatus(mapOrder);
    }

    @PostMapping("/_internal/orders/sync")
    public ResponseEntity<Void> synchronizeOrders() {
        log.info("demande sync recu");
        usersOrderInitializer.synchronize();
        return ResponseEntity.ok().build();
    }
}

