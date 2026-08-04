package in.ecommerce.ecommerce_backend.service;

import in.ecommerce.ecommerce_backend.entity.DeliveryOption;
import in.ecommerce.ecommerce_backend.repository.DeliveryOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryOptionService {

    private final DeliveryOptionRepository repository;

    public List<DeliveryOption> getAllDeliveryOptions() {
        return repository.findAll();
    }
}