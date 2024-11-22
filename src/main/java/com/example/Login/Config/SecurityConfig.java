package com.example.Login.Config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
//@ComponentScan(basePackages = {"com.example.Login.Profiles.Service", "com.example.Login.Login.Service"})
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers("/api/Driver/**").permitAll()
                        .requestMatchers("/api/Guest/**").permitAll()
                        .requestMatchers("/api/Booking/**").permitAll()
                        .requestMatchers("/api/Property/**").permitAll()
                        .requestMatchers("/api/login/verifyMail/{user_id}").permitAll()
                        .requestMatchers("/api/login/verifyOTP").permitAll()
                        .requestMatchers("/api/login/changePassword").permitAll()
                        .requestMatchers("/api/RoomPackage/**").permitAll()
                        .requestMatchers("/api/profile/deleteProfile/**").hasRole("ADMIN")
<<<<<<< HEAD
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
                        .requestMatchers("/api/**").authenticated()
=======
                        .requestMatchers("/rooms/**").permitAll()
                        .requestMatchers("/roomsPackages/**").permitAll()
>>>>>>> 1dffd7ce86415d9d40d5a63400610086852ec352
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint())
                )
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/rooms/**")
                .addResourceLocations("file:src/main/resources/static/rooms/")
                .addResourceLocations("file:src/main/resources/static/roomsPackages/") // Use 'file:' prefix
                .setCachePeriod(3600)
                .resourceChain(true);
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

}
