package eth.docs;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "My ETH API", version = "1.0.0",
        contact = @Contact(name = "Keem", email = "web3made@gmail.com"),
        license = @License(name = "Apache 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0.html")))
public class SwaggerDocs extends Application {

}
