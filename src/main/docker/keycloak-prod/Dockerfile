# Use the Keycloak image from Quay.io
# name the first build state as "builder"
FROM quay.io/keycloak/keycloak:latest as builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_LOG_LEVEL=DEBUG
# Copy the SSL certificates into the container
# COPY certs/localhostcert.pem /opt/keycloak/certs/localhostcert.pem
# COPY certs/localhostkey.pem /opt/keycloak/certs/localhostkey.pem

# # Set environment variables for Keycloak
# ENV KC_HTTPS_CERTIFICATE_FILE=/opt/keycloak/certs/localhostcert.pem
# ENV KC_HTTPS_CERTIFICATE_KEY_FILE=/opt/keycloak/certs/localhostkey.pem
# Define working directory for any RUN, CMD, ENTRYPOINT, COPY, ADD instructions that follow
WORKDIR /opt/keycloak

# for demonstration purposes only, please make sure to use proper certificates in production instead
# generate self-signed SSL certificate and set the password, storetype, keyalg, keysize, domain name, alias server, and keystore config
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
# build the keycloak server to optimize startup and runtime behaviour to save time  (would be in separate step in CI/CD pipeline)
RUN /opt/keycloak/bin/kc.sh build
# Second build step
FROM quay.io/keycloak/keycloak:latest
# Copy from the first "builder" stage, the /opt/keycloak/ diretory into /opt/keycloak/ directory for current build stage
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# Set env vars
ENV KC_HOSTNAME=localhost
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

# Expose the port Keycloak will run on
EXPOSE 8443
# Start keycloak in production mode
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]


# ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--https-certificate-file=/opt/keycloak/certs/localhostcert.pem", "--https-certificate-key-file=/opt/keycloak/certs/localhostkey.pem", "--verbose"]


