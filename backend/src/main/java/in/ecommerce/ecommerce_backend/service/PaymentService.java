package in.ecommerce.ecommerce_backend.service;

import in.ecommerce.ecommerce_backend.dto.PaymentSummary;
import in.ecommerce.ecommerce_backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CartService cartService;

    public PaymentSummary getSummary(User user) {

        Integer totalItems = cartService.getTotalItems(user);
        Integer productCost = cartService.getItemsTotal(user);



        Integer shipping = cartService.getShippingCost(user);

        Integer totalBeforeTax = productCost + shipping;

        Integer tax = (int) (totalBeforeTax * 0.10);

        Integer total = totalBeforeTax + tax;

        return new PaymentSummary(
                totalItems,
                productCost,
                shipping,
                totalBeforeTax,
                tax,
                total
        );
    }
}