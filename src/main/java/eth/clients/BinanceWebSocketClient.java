package eth.clients;

import org.jboss.logging.Logger;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.http.HttpClient;
import io.vertx.mutiny.core.http.WebSocketClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BinanceWebSocketClient {

    private static final String WEBSOCKET_HOST = "stream.binance.com";

    private static final String WEBSOCKET_URI = "/ws/ethusdt@trade";
    @Inject
    Vertx vertx;
    @Inject
    Logger logger;

    @PostConstruct
    public void connect() {
        HttpClient client = vertx.createHttpClient();

        WebSocketClient webSocketClient = new WebSocketClient(client);
        // connect() method established the Websocket connection using the new method
        webSocketClient.connect(WEBSOCKET_HOST, WEBSOCKET_URI).subscribe()
                .with(webSocket -> webSocket.handler(buffer -> {
                    String message = buffer.toString();
                    System.out.println("Message received " + message);
                    logger.info("Received message: " + message);
                }), failure -> logger.error("Failed to connect to WebSocket: ", failure));
    }

}
