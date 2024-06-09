package eth.services;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import eth.interfaces.BinanceClient;
import eth.repositories.UserRepository;
import eth.types.PricePoint;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PriceService {

    @Inject
    @RestClient
    BinanceClient binanceClient;

    @Inject
    UserRepository userRepo;

    private List<PricePoint> allPricePoints = new ArrayList<>();

    public Uni<Object> getEthPrice() {
        return binanceClient.getPrice("ETHUSDT").onItem().transform(res -> res.price);
    }

    public List<PricePoint> getAllPricePoints() {
        return this.allPricePoints;
    }

    public void setAllPricePoints() {
        userRepo.getAllPricePoints().subscribe().with(pricePoints -> {
            this.allPricePoints = pricePoints;
        });
    }

    // This service automatically consumes price data from WebSocket
    // Would want a service method to handle user-inputted price filter FIRST and THEN call event
    // bus
    // to trigger the notification service
    @ConsumeEvent("eth-price")
    public Uni<String> consumeEthPrice(String ethPrice) {
        System.out.println("eth-price service recieved " + ethPrice);
        return Uni.createFrom().item(() -> ethPrice.toUpperCase());
    }



}
