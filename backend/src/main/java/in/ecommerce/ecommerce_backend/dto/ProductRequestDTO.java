package in.ecommerce.ecommerce_backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double ratingStars;

    @NotNull
    @Min(0)
    private Integer ratingCount;

    private List<String> keywords;
}