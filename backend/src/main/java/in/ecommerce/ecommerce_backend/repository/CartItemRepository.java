package in.ecommerce.ecommerce_backend.repository;

import in.ecommerce.ecommerce_backend.entity.CartItem;
import in.ecommerce.ecommerce_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProductId(User user, String productId);

    void deleteByUser(User user);

}