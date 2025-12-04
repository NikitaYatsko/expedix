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

                        // Auth
                        .requestMatchers("/auth/**").permitAll()

                        // AGENT
                        .requestMatchers("/api/orders/me").hasRole("AGENT")
                        .requestMatchers("/api/orders/me/{id}").hasRole("AGENT")
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasRole("AGENT")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/me/**").hasRole("AGENT")
                        .requestMatchers(HttpMethod.POST, "/api/shops").hasRole("AGENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/shops/**").denyAll() // агенты не могут удалять

                        // DIRECTOR и OPERATOR на заказы
                        .requestMatchers("/api/orders/**").hasAnyRole("DIRECTOR", "OPERATOR")
                        .requestMatchers(HttpMethod.GET, "/api/orders/user/**").hasAnyRole("DIRECTOR", "OPERATOR")
                        .requestMatchers(HttpMethod.GET, "/api/orders/all").hasAnyRole("DIRECTOR", "OPERATOR", "FORWARDER")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAnyRole("DIRECTOR", "OPERATOR", "FORWARDER")

                        // DIRECTOR и OPERATOR на settlements
                        .requestMatchers("/api/settlements/**").hasAnyRole("DIRECTOR", "OPERATOR")

                        // Profile
                        .requestMatchers("/profile").authenticated()

                        // Публичные GET
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/settlements/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/shops/**").permitAll()

                        // Всё остальное запрещено
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