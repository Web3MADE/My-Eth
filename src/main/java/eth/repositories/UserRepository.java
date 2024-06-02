package eth.repositories;

import java.util.List;
import eth.entities.User;
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



}
