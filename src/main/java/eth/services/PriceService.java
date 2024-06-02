package eth.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import eth.interfaces.BinanceClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PriceService {

    @Inject
    @RestClient
    BinanceClient binanceClient;

    public Uni<Object> getEthPrice() {
        return binanceClient.getPrice("ETHUSDT").onItem().transform(res -> res.price);
    }



}
