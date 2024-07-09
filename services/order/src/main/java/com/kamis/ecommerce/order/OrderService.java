package com.kamis.ecommerce.order;

import com.kamis.ecommerce.customer.CustomerClient;
import com.kamis.ecommerce.exception.BusinessException;
import com.kamis.ecommerce.kafka.OrderConfirmation;
import com.kamis.ecommerce.kafka.OrderProducer;
import com.kamis.ecommerce.orderline.OrderLineRequest;
import com.kamis.ecommerce.orderline.OrderLineService;
import com.kamis.ecommerce.product.ProductClient;
import com.kamis.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderResquest request) {

        //check the customer if is present or not --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No customer exists with the provided ID:: " + request.customerId()));

        // purchase the product (it using the product microservice) (We'll use RestTemplate of OpenFeign)

        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        // persist order

        var order = this.repository.save(mapper.toOrder(request));

        // persist the order lines

        for (PurchaseRequest purchaseRequest: request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo start payment process

        //send the order confirmation to our notification microservice (Kafka)

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()-> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
