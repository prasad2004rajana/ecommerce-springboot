package in.ecommerce.ecommerce_backend.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    private String id;
    private String name;
    private String image;
    @Embedded
    private Rating rating;

    private Integer priceCents;


    @ElementCollection

    private List<String> keywords;
}
