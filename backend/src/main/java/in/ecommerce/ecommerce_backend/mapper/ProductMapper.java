package in.ecommerce.ecommerce_backend.mapper;


import in.ecommerce.ecommerce_backend.dto.ProductResponse;
import in.ecommerce.ecommerce_backend.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponseDTO(Product product);

    List<ProductResponse> toResponseDTOList(List<Product> products);
}