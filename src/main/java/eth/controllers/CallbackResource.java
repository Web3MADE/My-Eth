package eth.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import com.microsoft.aad.msal4j.AuthorizationCodeParameters;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import eth.services.AuthService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/callback")
public class CallbackResource {

    @Inject
    AuthService authService;

    @GET
    public Response handleCallback(@QueryParam("code") String code,
            @QueryParam("state") String state, @Context SecurityContext ctx) {
        try {
            ConfidentialClientApplication app =
                    ConfidentialClientApplication
                            .builder("{your-client-id}",
                                    ClientCredentialFactory
                                            .createFromSecret("{your-client-secret}"))
                            .authority("https://login.microsoftonline.com/{tenant}").build();

            AuthorizationCodeParameters parameters = AuthorizationCodeParameters
                    .builder(code, new URI("http://localhost:8080/callback"))
                    .scopes(Collections.singleton("api://{your-api-audience}/.default")).build();

            CompletableFuture<IAuthenticationResult> future = app.acquireToken(parameters);
            IAuthenticationResult result = future.join();

            // Manually set up the SecurityContext with the authenticated user information
            SecurityIdentity identity =
                    QuarkusSecurityIdentity.builder().setPrincipal(new Principal() {
                        @Override
                        public String getName() {
                            return result.account().username();
                        }
                    }).addRoles(Collections.singleton("user")) // Add roles if needed
                            .build();

            // Update the security context with the new identity
            ctx.getUserPrincipal();

            // Handle successful authentication and token acquisition
            return Response.ok("User authenticated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authentication failed")
                    .build();
        }
    }

}
