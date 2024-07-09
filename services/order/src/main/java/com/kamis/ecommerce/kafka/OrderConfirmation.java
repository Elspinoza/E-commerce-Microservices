package com.kamis.ecommerce.kafka;

import com.kamis.ecommerce.customer.CustomerResponse;
import com.kamis.ecommerce.order.PaymentMethod;
import com.kamis.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
//        They will present the list of all product the client purchase
        List<PurchaseResponse> products
) {
}
