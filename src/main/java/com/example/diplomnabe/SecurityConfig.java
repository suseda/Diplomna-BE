package com.example.diplomnabe;

import com.example.diplomnabe.Configs.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }


    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers( "/api/version1/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS).permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        List<String> options = new LinkedList<>();
        options.add("GET");
        options.add("POST");
        options.add("PUT");
        options.add("PATCH");
        options.add("DELETE");
        options.add("OPTIONS");
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.setAllowedMethods(options);
        source.registerCorsConfiguration("/**", config);


        return new CorsFilter(source);
    }
}
