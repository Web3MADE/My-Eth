package eth.websockets;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws/ethprice")
@ApplicationScoped
public class EthPriceWebSocket {

    @OnMessage
    public Uni<Void> onMessage(String message, Session session) {
        return Uni.createFrom().voidItem();
    }
}
