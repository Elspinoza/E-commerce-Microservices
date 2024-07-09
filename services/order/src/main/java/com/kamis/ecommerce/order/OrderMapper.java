package com.kamis.ecommerce.order;

import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class OrderMapper {
    public Order toOrder(OrderResquest request) {
        return Order.builder()
                .id(request.id())
                .customerId(request.customerId())
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }
}
