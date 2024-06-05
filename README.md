# My Eth App

## Dev notes

1. Start the Keycloak server in a local Docker container by navigating to `/src/main/docker/keycloak` and running `docker-compose up` in the terminal - this will start a Keycloak server on `http://localhost:8084/admin`

- Then, create a new realm that matches the `quarkus.oidc.auth-server-url` value realm suffix
- Inside the new realm, you can create new users and define authentication and authorization config
- To sign in to the Account view, as a user, go to `http://localhost:8084/realms/myrealm/account`
- Thanks to `quarkus-keycloak-authorization` we can set roles/permissions in the keycloak admin console which abstracts the authorization logic AWAY from the application code.

The flow is:

1. Keycloak server is started
2. Quarkus dev server is started
3. User signs in and gets redirected to Keycloak's registration UI
4. On success, they are redirected back to my-eth-app

- In the `/callback` method , the user is saved into MongoDB

5. Now the user is authenticated AND authorized to access protected resources labelled with `@RolesAllowed({"user"})`

Example registration URL:

```
http://localhost:8084/realms/{your-realm}/protocol/openid-connect/registrations?client_id={your-client-id}&response_type=code&scope=openid%20profile%20email&redirect_uri=http://localhost:8080/callback
```

Replace `{your-realm}`, `{your-client-id}`, and `http://localhost:8080/callback` with your actual realm, client ID, and callback URL.

3. **Handle the Callback**:
   - After successful registration, Keycloak will redirect the user back to your application with an authorization code.
   - Exchange the authorization code for an access token.

### Example Redirect and Callback Handling

**Redirect to Keycloak**:

```java
@Path("/signup")
public class SignUpResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response redirectToKeycloak() {
        String keycloakUrl = "http://localhost:8084/realms/{your-realm}/protocol/openid-connect/registrations?client_id={your-client-id}&response_type=code&scope=openid%20profile%20email&redirect_uri=http://localhost:8080/callback";
        return Response.status(Response.Status.FOUND).location(URI.create(keycloakUrl)).build();
    }
}
```

**Handle Callback**:

```java
@Path("/callback")
public class CallbackResource {

    @Inject
    KeycloakService keycloakService;

    @GET
    public Response handleCallback(@QueryParam("code") String code) {
        if (code != null) {
            // Exchange code for access token
            AccessTokenResponse tokenResponse = keycloakService.exchangeCodeForToken(code);

            // Get user info from Keycloak using the access token
            UserInfo userInfo = keycloakService.getUserInfo(tokenResponse.getToken());

            // Save user details in MongoDB
            User user = new User();
            user.username = userInfo.getPreferredUsername();
            user.email = userInfo.getEmail();
            userService.createUser(user);

            // Return success response
            return Response.ok("User registered successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Missing authorization code").build();
    }
}
```

**KeycloakService Methods**:

```java
public AccessTokenResponse exchangeCodeForToken(String code) {
    Keycloak keycloak = Keycloak.getInstance(
            "http://localhost:8084/auth",
            "myrealm",
            "my-client-id",
            "my-client-secret",
            "my-client-secret");

    return keycloak.tokenManager().getAccessToken();
}

public UserInfo getUserInfo(String accessToken) {
    Keycloak keycloak = Keycloak.getInstance(
            "http://localhost:8084/auth",
            accessToken,
            "myrealm");

    return keycloak.tokenManager().getUserInfo();
}
```

### Summary

- **Simpler Approach**: Redirect users to Keycloak's registration page and handle the callback to retrieve and save user information.
- **Less Code**: Avoids the need to use the Keycloak Admin API directly in your application.
- **Secure and Standard**: Utilizes standard OAuth2/OIDC flows provided by Keycloak.

By redirecting users to Keycloak's registration page, you simplify the user sign-up process and leverage Keycloak's built-in features.
