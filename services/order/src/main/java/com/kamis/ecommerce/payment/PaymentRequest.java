package com.kamis.ecommerce.payment;

import com.kamis.ecommerce.customer.CustomerResponse;
import com.kamis.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
