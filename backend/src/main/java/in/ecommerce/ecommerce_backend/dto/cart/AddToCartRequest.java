package in.ecommerce.ecommerce_backend.dto.cart;

import lombok.Data;

@Data
public class AddToCartRequest {

    private String productId;
    private Integer quantity;

}