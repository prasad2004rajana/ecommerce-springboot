package in.ecommerce.ecommerce_backend.service;

import in.ecommerce.ecommerce_backend.entity.CartItem;
import in.ecommerce.ecommerce_backend.entity.Product;
import in.ecommerce.ecommerce_backend.entity.User;
import in.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import in.ecommerce.ecommerce_backend.repository.CartItemRepository;
import in.ecommerce.ecommerce_backend.repository.DeliveryOptionRepository;
import in.ecommerce.ecommerce_backend.repository.ProductRepository;
import in.ecommerce.ecommerce_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DeliveryOptionRepository deliveryOptionRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<CartItem> getCart(User user) {
        return cartRepository.findByUser(user);
    }
    public CartItem addToCart(User user, String productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartRepository
                .findByUserAndProductId(user, productId)
                .orElse(null);
        var defaultDeliveryOption = deliveryOptionRepository
                .findById("1")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery option not found"));


        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(quantity)
                    .deliveryOption(defaultDeliveryOption)
                    .build();
        }

        return cartRepository.save(cartItem);
    }
    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }
    public CartItem updateQuantity(Long cartItemId, Integer quantity) {

        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);

        return cartRepository.save(cartItem);
    }
    public CartItem updateDeliveryOption(Long cartItemId, String deliveryOptionId) {

        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        var deliveryOption = deliveryOptionRepository.findById(deliveryOptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery option not found"));

        cartItem.setDeliveryOption(deliveryOption);

        return cartRepository.save(cartItem);
    }
    public Integer getShippingCost(User user) {

        return cartRepository.findByUser(user)
                .stream()
                .mapToInt(item -> item.getDeliveryOption().getPriceCents())
                .sum();
    }

    public Integer getTotalItems(User user) {

        return cartRepository.findByUser(user)
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    public Integer getItemsTotal(User user) {

        return cartRepository.findByUser(user)
                .stream()
                .mapToInt(item ->
                        item.getProduct().getPriceCents() * item.getQuantity())
                .sum();
    }

}