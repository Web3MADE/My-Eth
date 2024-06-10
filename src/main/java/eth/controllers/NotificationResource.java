package eth.controllers;

import eth.services.NotificationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

@Path("/api/notifications")
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    // @GET
    // @Path("eth-price")
    // // You want to filter eth-price FIRST, otherwise just call normal binance api for spot price
    // public Uni<String> getLatestEthPrice() {

    // }


}
