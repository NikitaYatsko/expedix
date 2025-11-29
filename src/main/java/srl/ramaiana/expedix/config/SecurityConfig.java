package srl.ramaiana.expedix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import srl.ramaiana.expedix.security.JwtRequestFilter;
import srl.ramaiana.expedix.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/settlements/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/shops/**").permitAll()

                        // Orders
                        .requestMatchers(HttpMethod.POST, "/api/orders/**")
                        .hasAnyRole("DIRECTOR", "OPERATOR", "AGENT")
                        .requestMatchers(HttpMethod.GET, "/api/orders/all")
                        .hasAnyRole("DIRECTOR", "OPERATOR", "FORWARDER")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**")
                        .hasAnyRole("DIRECTOR", "OPERATOR", "FORWARDER")
                        .requestMatchers(HttpMethod.PATCH, "/api/orders/**")
                        .hasAnyRole("DIRECTOR", "OPERATOR", "FORWARDER")

                        // Users
                        .requestMatchers("/api/users/**")
                        .hasAnyRole("DIRECTOR", "OPERATOR")

                        // Settlements, shops
                        .requestMatchers(HttpMethod.POST, "/api/settlements/**")
                        .hasAnyRole("DIRECTOR", "OPERATOR")

                        // Profile
                        .requestMatchers("/api/me/**").authenticated()

                        // Everything else
                        .anyRequest().denyAll()
                )

                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}