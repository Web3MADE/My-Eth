package eth.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/callback")
public class CallbackResource {

    @GET
    public Response handleCallback(@QueryParam("code") String code) {
        try {
            System.out.println("Code " + code);
            String clientId = "d8e998b0-53df-4fcc-b71f-42bb01c2f117";
            String clientSecret = "Gtp8Q~2rdbEJfpQMslW7geZOyrvCXFNxj7TqbaX3";
            String tenantId = "7fd94692-bb1d-4bdf-98e9-c541e299481b";
            String redirectUri = "http://localhost:8080/callback";

            URL url = URI
                    .create("https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token")
                    .toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String data = "client_id=" + URLEncoder.encode(clientId, "UTF-8") + "&scope="
                    + URLEncoder.encode("api://" + clientId + "/.default", "UTF-8") + "&code="
                    + URLEncoder.encode(code, "UTF-8") + "&redirect_uri="
                    + URLEncoder.encode(redirectUri, "UTF-8") + "&grant_type=authorization_code"
                    + "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = data.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br =
                    new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return Response.ok(response.toString()).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

}
