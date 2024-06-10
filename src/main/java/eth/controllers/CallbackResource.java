package eth.controllers;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/callback")
@Tag(name = "Callback Resource",
        description = "Required by Keycloak to handle redirects on Authentication")
public class CallbackResource {

    @Inject
    JsonWebToken jwt;

    @GET
    public Uni<Response> handleCallback() {
        String username = jwt.getName();
        return Uni.createFrom()
                .item(Response.ok("User " + username + " authenticated successfully").build());
    }

}
