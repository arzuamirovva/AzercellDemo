package com.finalproject.azercell.configuration.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;



    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(encoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
//                                .anyRequest().hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/user/").hasAnyRole("ADMIN","CUSTOMER")
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/tariff/**").authenticated()
                                .requestMatchers("/tariff/**").hasRole("ADMIN")
                                .requestMatchers("/spin").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.GET,"/apps/**").authenticated()
                                .requestMatchers("/apps/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/billing/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/balance-history/**").authenticated()
                                .requestMatchers("/card/**").authenticated() //
                                .requestMatchers(HttpMethod.PUT,"/numbers/**").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/number/status/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/numbers/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/numbers/remove-tariff/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/numbers/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/numbers").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/numbers/istesen/total-charge/").authenticated()
//                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(permitSwagger).permitAll()
                                .requestMatchers("/auth/register","/auth/login").permitAll()
                                .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                );
        return http.build();

    }

    public static String[] permitSwagger = {
            "/api/v1/auth/**",
            "v3/api-docs/**",
            "v3/api-docs.yaml",
            "swagger-ui/**",
            "swagger-ui.html"
    };
}
