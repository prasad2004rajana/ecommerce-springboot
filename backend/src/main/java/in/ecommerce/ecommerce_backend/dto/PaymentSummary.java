package in.ecommerce.ecommerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentSummary {

    private Integer totalItems;
    private Integer productCostCents;
    private Integer shippingCostCents;
    private Integer totalCostBeforeTaxCents;
    private Integer taxCents;
    private Integer totalCostCents;
}