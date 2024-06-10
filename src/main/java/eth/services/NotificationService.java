package eth.services;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificationService {

    @Inject
    EventBus eventBus;

    @ConsumeEvent("eth-price")
    public Uni<String> consumeEthPrice(String ethPrice) {
        System.out.println("eth-price consumed " + ethPrice);
        return Uni.createFrom().item(() -> ethPrice.toUpperCase());
    }

    public void publishEthPriceEvent(String ethPrice) {
        eventBus.publish("eth-price", ethPrice);
    }

    // notification service method to notify user (push notification/email)
    // how to mock this in UI?



}
