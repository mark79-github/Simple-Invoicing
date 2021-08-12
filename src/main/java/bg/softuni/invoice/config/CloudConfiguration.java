package bg.softuni.invoice.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class CloudConfiguration {

    @Value("${cloudinary.cloud-name}")
    private String cloudApiName;
    @Value("${cloudinary.api-key}")
    private String cloudApiKey;
    @Value("${cloudinary.api-secret}")
    private String cloudApiSecret;

    @Bean
    public Cloudinary cloudinary() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("cloud_name", cloudApiName);
        map.put("api_key", cloudApiKey);
        map.put("api_secret", cloudApiSecret);
        return new Cloudinary(map);
    }
}
