package in.ecommerce.ecommerce_backend.repository;

import in.ecommerce.ecommerce_backend.entity.Order;
import in.ecommerce.ecommerce_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    @Query("""
        SELECT oi
        FROM OrderItem oi
        JOIN FETCH oi.product
        WHERE oi.order IN :orders
        """)
    List<OrderItem> findByOrderIn(@Param("orders") List<Order> orders);

    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, String productId);

}