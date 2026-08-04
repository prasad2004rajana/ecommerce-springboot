package in.ecommerce.ecommerce_backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.ecommerce.ecommerce_backend.entity.Product;
import in.ecommerce.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {

        if (productRepository.count() == 0) {



            InputStream inputStream =
                    new ClassPathResource("products.json").getInputStream();

            List<Product> products =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<List<Product>>() {}
                    );

            productRepository.saveAll(products);

           
        }
    }
}