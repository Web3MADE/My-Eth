package eth.services;

import java.math.BigDecimal;
import eth.entities.User;
import eth.types.PriceEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificationService {

    @Inject
    EventBus eventBus;

    @Inject
    UserService userService;

    @ConsumeEvent("eth-price")
    public void notifyUser(PriceEvent priceEvent) {
        String userId = priceEvent.getUserId();
        BigDecimal price = priceEvent.getPrice();

        userService.getUserById(userId).onItem().ifNotNull().transformToUni(user -> {
            sendNotification(user, price);
            return Uni.createFrom().voidItem();
        }).onItem().ignore().andContinueWithNull().subscribe().with(ignored -> {
        }, failure -> System.err.println("Failed to send notification: " + failure));
    }

    public void publishEthPriceEvent(String ethPrice) {
        eventBus.publish("eth-price", ethPrice);
    }

    private void sendNotification(User user, BigDecimal price) {
        // logic to send email or push notificaiton
    }
}
