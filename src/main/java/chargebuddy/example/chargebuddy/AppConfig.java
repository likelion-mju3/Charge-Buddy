package chargebuddy.example.chargebuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Getter
    public static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Getter
    public static String serviceKey;

    @Value("${custom.api.serviceKey}")
    public void setServiceKey(String serviceKey){
        this.serviceKey = serviceKey;
    }

    @Getter
    public static String reviewKey;

    @Value("${custom.api.reviewKey}")
    public void setReviewKey(String reviewKey){
        this.reviewKey = reviewKey;
    }


    @Getter
    public static String localKey;

    @Value("${custom.api.localKey}")
    public void setLocalKey(String localKey){
        this.localKey = localKey;
    }


    @Getter
    public static String summeryKey;

    @Value("${custom.api.summeryKey}")
    public void setSummeryKey(String summeryKey){
        this.summeryKey = summeryKey;
    }
}
