package com.example.LendBuddy.security.configs;

import com.example.LendBuddy.security.filters.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {
    private final JwtAuthFilter jwtAuthFilter;
//    private final OAuth2SuccessHandler auth2SuccessHandler;
private final String[] publicRoutes = {
        "/auth/**",
        "/error",
        "/home.html",

        // Swagger UI & OpenAPI
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",

        // Actuator
        "/actuator/**"
};

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter ){
        this.jwtAuthFilter = jwtAuthFilter;
//        this.auth2SuccessHandler = auth2SuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(
                        auth->
                                auth
                                        .requestMatchers(publicRoutes).permitAll()
                                        .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
                                        .requestMatchers(HttpMethod.POST,"/posts/**")
                                        .hasAnyRole("ADMIN,USER")
//                                       .requestMatchers(HttpMethod.POST,"/posts/**")
//                                            .hasAnyAuthority(POST_CREATE.name())
//                                       .requestMatchers(HttpMethod.GET,"/posts/**")
//                                            .hasAnyAuthority(POST_VIEW.name())
                                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .csrf(csrfConfig-> csrfConfig.disable())
                .sessionManagement(sessionConifg->sessionConifg.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(oauth2Config->oauth2Config
//                        .failureUrl("/login?error=true")
//                        .successHandler(auth2SuccessHandler)
//                );

        return httpSecurity.build();
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

}
