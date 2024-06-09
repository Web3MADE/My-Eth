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

    public static class SetPricePointRequest {
        String userId;
        PricePoint pricePoint;

        public SetPricePointRequest(String userId, PricePoint pricePoint) {
            this.userId = userId;
            this.pricePoint = pricePoint;
        }
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
    public Uni<PricePoint> setPricePoint(SetPricePointRequest pricePointRequest) {
        return priceService.setPricePoint(pricePointRequest.userId, pricePointRequest.pricePoint);
    }


}
