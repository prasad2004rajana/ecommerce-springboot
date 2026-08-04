package in.ecommerce.ecommerce_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DeliveryOption {

    @Id
    private String id;

    private Integer deliveryDays;

    private Integer priceCents;
}