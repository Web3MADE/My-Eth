package eth.services;

import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificationService {

    @Inject
    EventBus eventBus;

    public void publishEthPriceEvent(String ethPrice) {
        eventBus.publish("eth-price", ethPrice);
    }


}
