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

    // TODO: Unit test to confirm logic works as intended
    @Scheduled(every = "30s")
    public void checkPrice() {
        priceService.getEthPrice().onItem().transform(ethPriceStr -> {
            BigDecimal ethPrice = new BigDecimal(ethPriceStr);
            System.out.println("30 second check " + ethPrice);
            return filterPricePoints(ethPrice);
        }).subscribe().with(filteredPricePoints -> {
            // Handle the filtered price points
            filteredPricePoints.forEach(pricePoint -> {
                pricePoint.setNotified(true);
                notificationService.publishEthPriceEvent(new String(pricePoint.toString()));
                System.out.println("Price point met: " + pricePoint);
            });
        });
    }

    public List<PricePoint> filterPricePoints(BigDecimal price) {
        return priceService.getAllPricePoints().stream()
                .filter(pricePoint -> pricePoint.getPricePoint().compareTo(price) >= 0
                        && !pricePoint.notified)
                .collect(Collectors.toList());

    }

}
