package eth.clients;

import org.jboss.logging.Logger;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import eth.services.NotificationService;
import eth.services.PriceService;
import eth.utils.JsonParser;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

@ApplicationScoped
public class BinanceWebSocketClient {

    @Inject
    PriceService priceService;

    @Inject
    NotificationService notificationService;

    @Inject
    Logger logger;

    private JsonParser jsonParser;

    // This is executed after dependency injection is done to perform any initialization.
    // It is part of the CDI lifecycle, called once the bean is constructed an all deps injected
    @PostConstruct
    public void connect() {
        try {
            jsonParser = new JsonParser();
            WebSocketStreamClient wsStreamClient = new WebSocketStreamClientImpl(); // defaults to
                                                                                    // live
                                                                                    // exchange
                                                                                    // unless
                                                                                    // stated.
            // fetch all user's price points from DB
            priceService.setAllPricePoints();
            System.out.println("all price points of all users " + priceService.getAllPricePoints());
            wsStreamClient.symbolTicker("ethusdt", ((event) -> {
                String ethPrice = jsonParser.parseEthPriceJSON(event);
                logger.info("Binance Event: Eth Price = " + ethPrice);
                notificationService.publishEthPriceEvent(ethPrice);
                // wsStreamClient.closeAllConnections();
            }));

        } catch (Error error) {
            System.out.println("Error in websocket: " + error.getMessage());
        }

    }

    // Observe the Startup event of the API, which is a different lifecycle event compared to
    // @PostConstruct
    private void on(@Observes Startup event) {

    }

}

