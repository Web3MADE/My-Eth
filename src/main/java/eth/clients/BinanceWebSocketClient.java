package eth.clients;

import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.http.WebSocketConnectOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.http.WebSocketClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BinanceWebSocketClient extends AbstractVerticle {
    @Inject
    Vertx vertx;

    @Inject
    WebSocketClient webSocketClient;

    @Override
    public void start() {
        WebSocketConnectOptions options = new WebSocketConnectOptions()
                .setURI("wss://stream.testnet.binance.vision/ws/ethusdt@trade");

        webSocketClient.connect(options).subscribe().with(ws -> ws.handler(buffer -> {
            String message = buffer.toString();
            // Broadcast the message to connected WebSocket clients
            broadcastMessage(message);
        }), Throwable::printStackTrace);

    }

    private void broadcastMessage(String message) {
        // Broadcast the message to all connected WebSocket clients
        // Implement the logic to send the message to all WebSocket sessions
    }
}
