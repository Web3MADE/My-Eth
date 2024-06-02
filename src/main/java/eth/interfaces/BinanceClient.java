package eth.interfaces;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/api/v3")
public interface BinanceClient {

    class BinancePriceResponse {
        public String symbol;
        public String price;
    }

    @GET
    Uni<BinancePriceResponse> getPrice(@QueryParam("symbol") String symbol);

}
