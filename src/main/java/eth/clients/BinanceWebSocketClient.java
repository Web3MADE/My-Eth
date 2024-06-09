package eth.clients;

import org.jboss.logging.Logger;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

@ApplicationScoped
public class BinanceWebSocketClient {
    @Inject
    Logger logger;

    // This is executed after dependency injection is done to perform any initialization.
    // It is part of the CDI lifecycle, called once the bean is constructed an all deps injected
    @PostConstruct
    public void connect() {
        try {
            System.out.println("Connect post construct BinanceWebSocketClient");
            WebSocketStreamClient wsStreamClient = new WebSocketStreamClientImpl(); // defaults to
                                                                                    // live
                                                                                    // exchange
                                                                                    // unless
                                                                                    // stated.

            wsStreamClient.symbolTicker("btcusdt", ((event) -> {
                logger.info("Binance Event: " + event);
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

