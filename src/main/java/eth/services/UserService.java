package eth.services;

import eth.entities.User;
import eth.repositories.UserRepository;
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



}
