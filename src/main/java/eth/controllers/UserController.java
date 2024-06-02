package eth.controllers;

import eth.entities.User;
import eth.services.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    public static class CreateUserRequest {
        public String name;
        public String email;
    }

    @Inject
    UserService userService;

    @POST
    @Path("/create")
    @PermitAll
    public Uni<User> createUser(CreateUserRequest userRequest) {

        if (userRequest.name == null || userRequest.email == null) {
            return Uni.createFrom()
                    .failure(new IllegalArgumentException("No userRequest.name or email passed!"));
        }

        return userService.createUser(userRequest.name, userRequest.email);
    }

    // TODO: this endpoint is still blocked by Azure -
    // SO CLOSE, we have JWT with User.Read, but this endpoint 401s every time....
    // @GET
    // @RolesAllowed({"User.Read"})
    // public String securedEndpoint(@Context SecurityContext ctx) {
    // System.out.println("endpoint called");
    // Principal caller = ctx.getUserPrincipal();
    // String name = caller == null ? "anonymous" : caller.getName();
    // String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s", name,
    // ctx.isSecure(), ctx.getAuthenticationScheme());
    // return helloReply;
    // }
}
