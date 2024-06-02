package eth.controllers;

import eth.services.PriceService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/price")
@ApplicationScoped
public class PriceResource {

    @Inject
    PriceService priceService;

    @GET
    @Path("/eth")
    @PermitAll
    public Uni<Object> getEthPrice() {
        return priceService.getEthPrice();
    }

}
