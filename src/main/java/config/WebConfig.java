package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Enable CORS for frontend (React)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // Your React app origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // Cache pre-flight response for 1 hour
    }

    // Serve static files (e.g., reports, screenshots, etc.)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Static folder inside your project root
        registry.addResourceHandler("/reports/**")
                .addResourceLocations("file:C:/Users/saurav.kumar/eclipse-workspace/CSFB_INTERNET_BANKING/CSFB_INTERNET_BANKING/TestReport/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:/Users/saurav.kumar/eclipse-workspace/CSFB_INTERNET_BANKING/CSFB_INTERNET_BANKING/TestReport/images/");
    }
}
