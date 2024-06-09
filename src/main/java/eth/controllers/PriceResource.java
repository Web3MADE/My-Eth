package eth.controllers;

import eth.services.PriceService;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/eth-price")
public class PriceResource {
    @Inject
    EventBus eventBus;

    @Inject
    PriceService priceService;

    @GET
    @PermitAll
    public Uni<Object> getEthPrice() {
        return priceService.getEthPrice();
    }

    // TODO: use SecurityIdentity to get current logged in user & fetch their pricePoints
    // This will be checked during the BinanceWebSocket stream process to see if any price points
    // are hit
    // If yes, then publish to EventBus (decouples services from each other)
    // and the notification service will listen for changes and send a notification to the user



}
