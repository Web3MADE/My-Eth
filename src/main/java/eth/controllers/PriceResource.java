package eth.controllers;

import eth.services.PriceService;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/price")
public class PriceResource {

    @Inject
    EventBus eventBus;

    @Inject
    PriceService priceService;

    @GET
    @Path("/eth")
    @PermitAll
    public Uni<Object> getEthPrice() {
        return priceService.getEthPrice();
    }

}
