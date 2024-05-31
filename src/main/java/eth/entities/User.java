package eth.entities;

import java.util.List;
import eth.utils.PricePoint;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "Users")
public class User extends PanacheMongoEntity {
    public String name;
    public String email;
    public List<PricePoint> pricePoints;

}
