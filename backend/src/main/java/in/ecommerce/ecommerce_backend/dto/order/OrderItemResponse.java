package in.ecommerce.ecommerce_backend.dto.order;

import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Integer quantity;
    private ProductResponse product;
}