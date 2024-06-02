package eth.entities;

import java.util.List;
import eth.types.PricePoint;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

// @MongoEntity(collection = "Users")
public class User extends ReactivePanacheMongoEntity {
    public String name;
    public String email;
    public List<PricePoint> pricePoints;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
