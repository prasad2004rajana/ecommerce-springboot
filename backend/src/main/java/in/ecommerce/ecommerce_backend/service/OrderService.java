package in.ecommerce.ecommerce_backend.service;

import org.springframework.transaction.annotation.Transactional;
import in.ecommerce.ecommerce_backend.entity.CartItem;
import in.ecommerce.ecommerce_backend.entity.Order;
import in.ecommerce.ecommerce_backend.entity.OrderItem;
import in.ecommerce.ecommerce_backend.entity.OrderStatus;
import in.ecommerce.ecommerce_backend.entity.User;
import in.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import in.ecommerce.ecommerce_backend.repository.CartItemRepository;
import in.ecommerce.ecommerce_backend.repository.OrderItemRepository;
import in.ecommerce.ecommerce_backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import in.ecommerce.ecommerce_backend.dto.order.TrackingResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartRepository;

    @Transactional
    public Order placeOrder(User user) {

        // Get all cart items of the user
        List<CartItem> cartItems = cartRepository.findByUser(user);

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create new order
        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        // Save order to generate Order ID
        order = orderRepository.save(order);

        Integer totalAmount = 0;

        // Convert CartItems into OrderItems
        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .priceCents(cartItem.getProduct().getPriceCents())
                    .build();

            orderItemRepository.save(orderItem);

            Integer itemTotal = cartItem.getProduct().getPriceCents()
                    * cartItem.getQuantity();

            totalAmount += itemTotal;
        }

        // Update total amount
        order.setTotalAmount(totalAmount);

        // Save updated order
        orderRepository.save(order);

        // Clear user's cart
        cartRepository.deleteByUser(user);

        return order;
    }

    public List<Order> getOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<OrderItem> getOrderItems(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public TrackingResponse getTracking(Long orderId, String productId) {

        OrderItem orderItem = orderItemRepository
                .findByOrderIdAndProductId(orderId, productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order item not found"));

        Order order = orderItem.getOrder();

        return TrackingResponse.builder()
                .product(
                        ProductResponse.builder()
                                .id(orderItem.getProduct().getId())
                                .name(orderItem.getProduct().getName())
                                .image(orderItem.getProduct().getImage())
                                .priceCents(orderItem.getProduct().getPriceCents())
                                .build()
                )
                .quantity(orderItem.getQuantity())
                .estimatedDeliveryDate(
                        order.getOrderDate()
                                .plusDays(7)
                                .toLocalDate()
                )
                .status(order.getStatus().name())
                .build();
    }
}