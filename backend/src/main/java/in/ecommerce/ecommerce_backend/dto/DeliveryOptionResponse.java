package in.ecommerce.ecommerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryOptionResponse {

    private String id;
    private Integer deliveryDays;
    private Integer priceCents;
    private String estimatedDeliveryTime;
}