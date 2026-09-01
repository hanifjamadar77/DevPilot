package com.Devpilot.Backend.config;

import com.Devpilot.Backend.security.GithubOAuth2UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final GithubOAuth2UserService githubOAuth2UserService;

    @Value("${app.frontend-url}")
    private String frontendUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler();

        successHandler.setDefaultTargetUrl(
                frontendUrl + "/auth/callback"
        );


        SimpleUrlAuthenticationFailureHandler failureHandler =
                new SimpleUrlAuthenticationFailureHandler();

        failureHandler.setDefaultFailureUrl(
                frontendUrl + "/login?error=oauth_failed"
        );


        http
                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/auth/login-url",
                                "/api/auth/debug",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/error"
                        ).permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        .requestMatchers("/api/**")
                        .authenticated()

                        .anyRequest()
                        .permitAll()
                )

                .oauth2Login(oauth -> oauth

                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(githubOAuth2UserService)
                        )

                        .successHandler(successHandler)

                        .failureHandler(failureHandler)
                )

                .logout(logout -> logout

                        .logoutUrl("/api/auth/logout")

                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.NO_CONTENT.value())
                        )

                        .invalidateHttpSession(true)

                        .clearAuthentication(true)

                        .deleteCookies("DEVPILOT_SESSION")
                );

        return http.build();
    }
}