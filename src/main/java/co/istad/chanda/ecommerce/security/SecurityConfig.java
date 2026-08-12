package co.istad.chanda.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // allow config security in controller
public class SecurityConfig {

    @Bean
    public SecurityFilterChain appFilterChain(HttpSecurity http) throws Exception {
        // Todo
        // 1. rest architecture - stateless
        http.sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 2. Endport policy (public/protected endpoint)
        http.authorizeHttpRequests(request -> request
                .requestMatchers(
                        "/swagger-ui/index.html/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll() // permitAll = not security

                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/categories/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAnyRole("ADMIN")

                .anyRequest().authenticated()
        );

        // 3. Authentication mechanism (http Basic Auth, JWT, OAuth2)
        http.httpBasic(Customizer.withDefaults());

        // 4. Disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
