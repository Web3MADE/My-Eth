package eth.services;

import java.util.List;
import org.bson.types.ObjectId;
import eth.entities.User;
import eth.repositories.UserRepository;
import eth.types.PricePoint;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepo;

    public Uni<User> createUser(String name, String email) {
        return userRepo.createUser(name, email);
    }

    public Uni<List<User>> getUsers() {
        return userRepo.getUsers();
    }

    public Uni<PricePoint> setPricePoint(String userId, PricePoint pricePoint) {
        ObjectId mappedObjectId = new ObjectId(userId);

        return userRepo.setPricePoint(mappedObjectId, pricePoint);
    }

    // TODO: use SecurityIdentity to get current logged in user & fetch their pricePoints
    // This will be checked during the BinanceWebSocket stream process to see if any price points
    // are hit
    // If yes, then publish to EventBus (decouples services from each other)
    // and the notification service will listen for changes and send a notification to the user
    public Uni<List<PricePoint>> getUserPricePoints(String userId) {
        ObjectId mappedObjectId = new ObjectId(userId);

        return userRepo.getUserPricePoints(mappedObjectId);
    }

    public Uni<List<PricePoint>> getAllPricePoints() {
        return userRepo.getAllPricePoints();
    }

}
