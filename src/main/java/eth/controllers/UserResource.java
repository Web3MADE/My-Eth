package eth.controllers;

import java.security.Principal;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import eth.entities.User;
import eth.services.UserService;
import eth.types.PricePoint;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User Controller",
        description = "Access and Set User Information, including user price-points")
public class UserResource {

    public static class CreateUserRequest {
        public String name;
        public String email;
    }

    @Inject
    UserService userService;

    @POST
    public Uni<User> createUser(CreateUserRequest userRequest) {

        if (userRequest.name == null || userRequest.email == null) {
            return Uni.createFrom()
                    .failure(new IllegalArgumentException("No userRequest.name or email passed!"));
        }

        return userService.createUser(userRequest.name, userRequest.email);
    }

    @GET
    public Uni<List<User>> getUsers() {
        return userService.getUsers();
    }

    @GET
    @Path("{userId}/price-points")
    public Uni<List<PricePoint>> getUserPricePoint(@PathParam("userId") String userId) {
        return userService.getUserPricePoints(userId);
    }


    @POST
    @Path("{userId}/price-points")
    public Uni<PricePoint> setPricePoint(@PathParam("userId") String userId,
            PricePoint pricePoint) {
        return userService.setPricePoint(userId, pricePoint);
    }

    @GET
    @Path("/secured")
    // TODO: understand how to set permissions/scopes to Resources
    // This decouples code from authorization settings
    // @RolesAllowed({"user"}) // This is configured in Keycloak NOT server code by
    // quarkus-keycloak-authorization extension, as it decouples Code auth from infrastructure auth
    public String securedEndpoint(@Context SecurityContext ctx) {
        System.out.println("Only Admins can access!");
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s", name,
                ctx.isSecure(), ctx.getAuthenticationScheme());
        return helloReply;
    }

}
