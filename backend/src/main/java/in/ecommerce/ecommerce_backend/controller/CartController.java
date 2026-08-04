package in.ecommerce.ecommerce_backend.controller;

import in.ecommerce.ecommerce_backend.dto.cart.AddToCartRequest;
import in.ecommerce.ecommerce_backend.dto.cart.CartItemResponse;
import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import in.ecommerce.ecommerce_backend.dto.cart.UpdateDeliveryOptionRequest;
import in.ecommerce.ecommerce_backend.entity.CartItem;
import in.ecommerce.ecommerce_backend.entity.User;
import in.ecommerce.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItemResponse> getCart(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = cartService.getUserByEmail(userDetails.getUsername());

        return cartService.getCart(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public CartItemResponse addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddToCartRequest request) {

        User user = cartService.getUserByEmail(userDetails.getUsername());

        CartItem item = cartService.addToCart(
                user,
                request.getProductId(),
                request.getQuantity());

        return toResponse(item);
    }

    @PutMapping("/{id}/delivery-option")
    public CartItemResponse updateDeliveryOption(
            @PathVariable Long id,
            @RequestBody UpdateDeliveryOptionRequest request) {

        return toResponse(
                cartService.updateDeliveryOption(
                        id,
                        request.getDeliveryOptionId()
                )
        );
    }
    @PutMapping("/{id}")
    public CartItemResponse updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        return toResponse(cartService.updateQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }

    @DeleteMapping
    public void clearCart(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = cartService.getUserByEmail(userDetails.getUsername());

        cartService.clearCart(user);
    }

    private CartItemResponse toResponse(CartItem item) {

        return CartItemResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .deliveryOptionId(item.getDeliveryOption().getId())
                .product(
                        ProductResponse.builder()
                                .id(item.getProduct().getId())
                                .name(item.getProduct().getName())
                                .image(item.getProduct().getImage())
                                .priceCents(item.getProduct().getPriceCents())
                                .build()
                )
                .build();
    }
}