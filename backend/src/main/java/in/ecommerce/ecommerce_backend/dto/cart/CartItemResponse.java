package in.ecommerce.ecommerce_backend.dto.cart;

import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long id;
    private Integer quantity;
    private String deliveryOptionId;
    private ProductResponse product;
}