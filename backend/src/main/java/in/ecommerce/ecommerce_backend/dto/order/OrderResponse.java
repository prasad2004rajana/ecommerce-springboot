package in.ecommerce.ecommerce_backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private LocalDateTime orderDate;
    private Integer totalAmount;
    private String status;
    private List<OrderItemResponse> items;
}