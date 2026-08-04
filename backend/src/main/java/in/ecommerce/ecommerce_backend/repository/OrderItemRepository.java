package in.ecommerce.ecommerce_backend.repository;

import in.ecommerce.ecommerce_backend.entity.Order;
import in.ecommerce.ecommerce_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, String productId);

}