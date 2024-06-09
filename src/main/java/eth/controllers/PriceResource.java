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

@Path("/api/eth-price")
public class PriceResource {

    public static class PricePointRequest {
        String userId;
        PricePoint pricePoint;
    }

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
    public Uni<PricePoint> setPricePoint(PricePointRequest pricePointRequest) {
        return priceService.setPricePoint(pricePointRequest.userId, pricePointRequest.pricePoint);
    }


}
