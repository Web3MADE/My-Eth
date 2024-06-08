package eth.websockets;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/** @dev Manages WebSocket sessions and broadcasts messages to all connected clients */
// This endpoint would be consumed by the frontend on the home page
@ServerEndpoint("/ws/ethprice")
@ApplicationScoped
public class EthPriceWebSocket {

    private static final ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    // This method level annotation enables the consumption of incoming websocket messages, only one
    // per server endpoint
    @OnMessage
    public Uni<Void> onMessage(String message, Session session) {
        return Uni.createFrom().voidItem();
    }

    public void broadcastMessage(String message) {
        sessions.values().forEach(session -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        });
    }

    @OnOpen
    public void onOpen(Session session) {
        // assign the current session to a key value (the session ID)
        sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        // remove session by ID
        sessions.remove(session.getId());
    }
}
