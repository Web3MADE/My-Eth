package eth.repositories;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import eth.entities.User;
import eth.types.PricePoint;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements ReactivePanacheMongoRepository<User> {

    public Uni<User> createUser(String name, String email) {
        User newUser = new User(name, email);
        return newUser.persist().onItem().transform(u -> newUser);
    }

    public Uni<User> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Uni<List<User>> getUsers() {
        return listAll();
    }

    public Uni<PricePoint> setPricePoint(ObjectId userId, PricePoint pricePoint) {
        return findById(userId).onItem().ifNotNull().transformToUni(user -> {
            if (user.pricePoints == null) {
                user.pricePoints = new ArrayList<>();
            }
            user.pricePoints.add(new PricePoint(pricePoint.price, pricePoint.notified));
            int index = user.pricePoints.size() - 1;
            return Uni.createFrom().item(user.pricePoints.get(index));
        });
    }

}
