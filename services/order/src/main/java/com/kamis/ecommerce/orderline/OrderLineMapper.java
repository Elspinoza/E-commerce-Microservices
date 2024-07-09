package com.kamis.ecommerce.orderline;

import com.kamis.ecommerce.order.Order;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .productId(request.productId())
                .build();
    }
}
