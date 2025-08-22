package chargebuddy.example.chargebuddy;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
