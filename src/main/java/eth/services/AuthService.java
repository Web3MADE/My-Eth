package eth.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class AuthService {

    @Inject
    SecurityContext securityContext;

    public Boolean isUserAuthenticated() {
        return securityContext.getUserPrincipal() != null;
    }

}
