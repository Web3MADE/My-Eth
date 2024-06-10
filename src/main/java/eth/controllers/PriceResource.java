package eth.controllers;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import eth.services.PriceService;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/eth-price")
@Tag(name = "Price Resource", description = "Get the latest ETH price from Binance")
public class PriceResource {
    @Inject
    EventBus eventBus;

    @Inject
    PriceService priceService;

    @GET
    public Uni<String> getEthPrice() {
        return priceService.getEthPrice();
    }

}
