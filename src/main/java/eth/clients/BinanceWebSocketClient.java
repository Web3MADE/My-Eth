package eth.clients;

import org.jboss.logging.Logger;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import eth.services.NotificationService;
import eth.utils.JsonParser;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

@ApplicationScoped
public class BinanceWebSocketClient {

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

            wsStreamClient.symbolTicker("ethusdt", ((event) -> {
                String ethPrice = jsonParser.parseEthPriceJSON(event);
                logger.info("Binance Event: Eth Price = " + ethPrice);
                // If user is authenticated, check their price points
                notificationService.publishEthPriceEvent(ethPrice);
                wsStreamClient.closeAllConnections();
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

