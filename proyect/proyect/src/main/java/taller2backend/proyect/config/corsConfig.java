package taller2backend.proyect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class corsConfig implements WebMvcConfigurer {
    //configuracion base de cors
    private CorsConfiguration builConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");//permite peticiones desde cualquier origen
        corsConfiguration.addAllowedHeader("*");//pemrite todos los encabezados headers
        corsConfiguration.addAllowedMethod("*");//Permite todos los metodos http
        corsConfiguration.addExposedHeader("Authorization");//permite mostrar el header autorization en laa respuestas
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", builConfig());
        return new CorsFilter(source);
    }

    //conf adicionales de cors
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//aplicar a todas las rutas
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }
}


