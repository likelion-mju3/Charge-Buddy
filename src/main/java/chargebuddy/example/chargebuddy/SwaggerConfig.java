package chargebuddy.example.chargebuddy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "https://chargebuddy.digital", description = "https 서버"),
                @Server(url = "http://chargebuddy.digital", description = "http 서버"),
                @Server(url = "http://localhost:8080", description = "local 서버")
        }
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("ChargeBuddy API")
                .version("1.0.0")
                .description("<h3>ChargeBuddy API 문서</h3>");

        return new OpenAPI().info(info);
    }
}
