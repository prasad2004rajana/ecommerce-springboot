package in.ecommerce.ecommerce_backend.repository;

import in.ecommerce.ecommerce_backend.entity.DeliveryOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOptionRepository
        extends JpaRepository<DeliveryOption, String> {
}