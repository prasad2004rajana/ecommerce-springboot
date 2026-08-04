package in.ecommerce.ecommerce_backend.config;

import in.ecommerce.ecommerce_backend.entity.DeliveryOption;
import in.ecommerce.ecommerce_backend.repository.DeliveryOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryOptionDataLoader implements CommandLineRunner {

    private final DeliveryOptionRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        DeliveryOption option1 = new DeliveryOption();
        option1.setId("1");
        option1.setDeliveryDays(7);
        option1.setPriceCents(0);

        DeliveryOption option2 = new DeliveryOption();
        option2.setId("2");
        option2.setDeliveryDays(3);
        option2.setPriceCents(499);

        DeliveryOption option3 = new DeliveryOption();
        option3.setId("3");
        option3.setDeliveryDays(1);
        option3.setPriceCents(999);

        repository.save(option1);
        repository.save(option2);
        repository.save(option3);
    }
}