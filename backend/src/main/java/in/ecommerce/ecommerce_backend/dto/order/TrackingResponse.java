package in.ecommerce.ecommerce_backend.dto.order;

import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResponse {

    private ProductResponse product;

    private Integer quantity;

    private LocalDate estimatedDeliveryDate;

    private String status;
}