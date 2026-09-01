package com.Devpilot.Backend.config;

import com.Devpilot.Backend.security.GithubOAuth2UserService;
import com.Devpilot.Backend.security.JwtTokenProvider;
import com.Devpilot.Backend.security.AppUserPrincipal;
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
import org.springframework.security.core.Authentication;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final GithubOAuth2UserService githubOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.frontend-url}")
    private String frontendUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    jakarta.servlet.http.HttpServletRequest request,
                    jakarta.servlet.http.HttpServletResponse response,
                    Authentication authentication) throws java.io.IOException {
                try {
                    // Extract user from authentication principal
                    AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
                    
                    // Generate JWT token
                    String token = jwtTokenProvider.generateToken(principal.getUser());
                    
                    // Redirect to frontend with token in URL
                    String redirectUrl = frontendUrl + "/auth/callback?token=" + 
                        java.net.URLEncoder.encode(token, "UTF-8");
                    response.sendRedirect(redirectUrl);
                } catch (Exception e) {
                    response.sendRedirect(frontendUrl + "/login?error=token_generation_failed");
                }
            }
        };


        SimpleUrlAuthenticationFailureHandler failureHandler =
                new SimpleUrlAuthenticationFailureHandler();

        failureHandler.setDefaultFailureUrl(
                frontendUrl + "/login?error=oauth_failed"
        );


        http
                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
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
                                "/api/auth/check-session",
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