package com.kamis.ecommerce.kafka.order;

import com.kamis.ecommerce.kafka.customer.Customer;
import com.kamis.ecommerce.kafka.payment.PaymentMethod;
import com.kamis.ecommerce.kafka.product.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {
}
