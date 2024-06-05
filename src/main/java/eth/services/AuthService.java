package eth.services;

import java.util.Collections;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {


    // In some other part of your code, make the client application (public or confidential).
    // This object will have an in-memory cache associated with it,
    // and the cache will exist for as long as the public/confidential client app object does
    // ...
    private final ConfidentialClientApplication app;

    // Handle authentication via MSAL4J library
    public AuthService() throws Exception {
        app = ConfidentialClientApplication
                .builder("d8e998b0-53df-4fcc-b71f-42bb01c2f117",
                        ClientCredentialFactory
                                .createFromSecret("Gtp8Q~2rdbEJfpQMslW7geZOyrvCXFNxj7TqbaX"))
                .authority(
                        "https://login.microsoftonline.com/7fd94692-bb1d-4bdf-98e9-c541e299481b/v2.0")
                .build();
    }

    public IAuthenticationResult acquireToken(String scope) throws Exception {
        ClientCredentialParameters parameters =
                ClientCredentialParameters.builder(Collections.singleton((scope))).build();
        return app.acquireToken(parameters).join();
    }
    // In some method for acquiring a token....
    // public Uni<IAuthenticationResult> getAToken() {
    // return Uni.createFrom().completionStage(() -> {
    // try {
    // PublicClientApplication pca = PublicClientApplication
    // .builder("d8e998b0-53df-4fcc-b71f-42bb01c2f117").build();
    // Set<IAccount> accountsInCache = pca.getAccounts().join();
    // IAccount account = accountsInCache.iterator().next();
    // Set<String> scopes = new HashSet<>();
    // scopes.add("openid");
    // scopes.add("email");
    // scopes.add("profile");

    // SilentParameters silentParameters =
    // SilentParameters.builder(scopes, account).build();
    // CompletableFuture<IAuthenticationResult> future =
    // pca.acquireTokenSilently(silentParameters);

    // return future.exceptionally(ex -> {
    // if (ex.getCause() instanceof MsalException) {
    // try {
    // InteractiveRequestParameters parameters = InteractiveRequestParameters
    // .builder(new URI("http://localhost")).scopes(scopes).build();
    // return pca.acquireToken(parameters).join();
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // } else {
    // throw new RuntimeException(ex);
    // }
    // });
    // } catch (Exception e) {
    // CompletableFuture<IAuthenticationResult> failedFuture = new CompletableFuture<>();
    // failedFuture.completeExceptionally(e);
    // return failedFuture;
    // }
    // });
    // }

}
