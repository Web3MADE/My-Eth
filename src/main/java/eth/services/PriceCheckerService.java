package eth.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import eth.types.PricePoint;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PriceCheckerService {

    @Inject
    NotificationService notificationService;

    @Inject
    PriceService priceService;

    @Scheduled(every = "30s")
    public void checkPrice() {

    }

    public List<PricePoint> filterPricePoints(BigDecimal price) {
        return priceService.getAllPricePoints().stream()
                .filter(pricePoint -> pricePoint.getPricePoint().compareTo(price) >= 0)
                .collect(Collectors.toList());

    }



}
