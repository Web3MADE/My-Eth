package eth.controllers;

import eth.services.PriceService;
import eth.types.PricePoint;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

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

    @POST
    @Path("{userId}")
    public Uni<PricePoint> setPricePoint(@PathParam("userId") String userId,
            PricePoint pricePoint) {
        return priceService.setPricePoint(userId, pricePoint);
    }


}
