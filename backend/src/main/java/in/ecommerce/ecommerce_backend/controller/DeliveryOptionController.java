package in.ecommerce.ecommerce_backend.controller;

import in.ecommerce.ecommerce_backend.dto.DeliveryOptionResponse;
import in.ecommerce.ecommerce_backend.entity.DeliveryOption;
import in.ecommerce.ecommerce_backend.service.DeliveryOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/delivery-options")
@RequiredArgsConstructor
public class DeliveryOptionController {

    private final DeliveryOptionService service;

    @GetMapping
    public List<DeliveryOptionResponse> getDeliveryOptions() {

        return service.getAllDeliveryOptions()
                .stream()
                .map(option -> new DeliveryOptionResponse(
                        option.getId(),
                        option.getDeliveryDays(),
                        option.getPriceCents(),
                        LocalDate.now()
                                .plusDays(option.getDeliveryDays())
                                .toString()
                ))
                .toList();
    }
}