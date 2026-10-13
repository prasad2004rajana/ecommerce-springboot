package in.ecommerce.ecommerce_backend.controller;

import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import in.ecommerce.ecommerce_backend.dto.order.OrderItemResponse;
import in.ecommerce.ecommerce_backend.dto.order.OrderResponse;
import in.ecommerce.ecommerce_backend.dto.order.TrackingResponse;
import in.ecommerce.ecommerce_backend.entity.Order;
import in.ecommerce.ecommerce_backend.entity.OrderItem;
import in.ecommerce.ecommerce_backend.entity.User;
import in.ecommerce.ecommerce_backend.service.CartService;
import in.ecommerce.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    public Order placeOrder(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = cartService.getUserByEmail(userDetails.getUsername());

        return orderService.placeOrder(user);
    }

    @GetMapping
    public List<OrderResponse> getOrders(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = cartService.getUserByEmail(userDetails.getUsername());

        // Fetch all orders for the user
        List<Order> orders = orderService.getOrders(user);

        // Fetch all order items in one query
        List<OrderItem> orderItems = orderService.getOrderItems(orders);

        return orders.stream()
                .map(order -> toResponse(order, orderItems))
                .toList();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {

        return orderService.getOrder(id);
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> getOrderItems(@PathVariable Long id) {

        Order order = orderService.getOrder(id);

        return orderService.getOrderItems(order);
    }

    @GetMapping("/{orderId}/items/{productId}")
    public TrackingResponse getTracking(
            @PathVariable Long orderId,
            @PathVariable String productId) {

        return orderService.getTracking(orderId, productId);
    }

    private OrderResponse toResponse(
            Order order,
            List<OrderItem> orderItems) {

        List<OrderItemResponse> items = orderItems.stream()
                .filter(item ->
                        item.getOrder().getId().equals(order.getId()))
                .map(item -> OrderItemResponse.builder()
                        .quantity(item.getQuantity())
                        .product(
                                ProductResponse.builder()
                                        .id(item.getProduct().getId())
                                        .name(item.getProduct().getName())
                                        .image(item.getProduct().getImage())
                                        .priceCents(item.getProduct().getPriceCents())
                                        .build()
                        )
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .items(items)
                .build();
    }
}