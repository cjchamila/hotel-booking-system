package com.hbm.booking_service.security;


<<<<<<< Updated upstream
=======
import org.springframework.context.annotation.Bean;
>>>>>>> Stashed changes
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;


    public SecurityConfig(JWTAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

<<<<<<< Updated upstream
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
=======
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return  http.csrf(AbstractHttpConfigurer::disable)
>>>>>>> Stashed changes
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/bookings/**").authenticated()
<<<<<<< Updated upstream
                        .anyRequest().permitAll());
=======
                        .anyRequest().permitAll())
                .build();
>>>>>>> Stashed changes
    }
}
