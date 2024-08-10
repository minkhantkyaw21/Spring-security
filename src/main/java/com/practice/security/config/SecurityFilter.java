package com.practice.security.config;

import com.practice.security.user.Permisssion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrfConfiguration -> csrfConfiguration.disable())
                .sessionManagement(sessionManagConfig ->
                        sessionManagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig ->{
                    authConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST,"/auth/register").permitAll();
                    authConfig.requestMatchers("/error").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET,"/products").hasAuthority(Permisssion.READ_ALL_PRODUCTS.name());
                    authConfig.requestMatchers(HttpMethod.POST,"/products").hasAuthority(Permisssion.SAVE_ONE_PRODUCT.name());

                    authConfig.anyRequest().denyAll();
                });
        return http.build();
    }
}
