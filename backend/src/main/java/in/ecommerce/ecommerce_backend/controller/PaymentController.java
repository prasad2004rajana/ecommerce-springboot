package in.ecommerce.ecommerce_backend.controller;

import in.ecommerce.ecommerce_backend.dto.PaymentSummary;
import in.ecommerce.ecommerce_backend.entity.User;
import in.ecommerce.ecommerce_backend.service.CartService;
import in.ecommerce.ecommerce_backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-summary")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final CartService cartService;

    @GetMapping
    public PaymentSummary getSummary(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = cartService.getUserByEmail(userDetails.getUsername());

        return paymentService.getSummary(user);
    }
}