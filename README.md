# My Eth App

## Dev notes

1. Start the Keycloak server in a local Docker container by navigating to `/src/main/docker/keycloak` and running `docker-compose up` in the terminal - this will start a Keycloak server on `http://localhost:8084/admin`

- Then, create a new realm that matches the `quarkus.oidc.auth-server-url` value realm suffix
- Inside the new realm, you can create new users and define authentication and authorization config
- To sign in to the Account view, as a user, go to `http://localhost:8084/realms/myrealm/account`
