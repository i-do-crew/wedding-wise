package com.idocrew.weddingwise.configs;


import com.idocrew.weddingwise.exception.CustomAccessDeniedHandler;
import com.idocrew.weddingwise.services.impl.AuthenticationSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandlerImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login-error")
                .permitAll() // Anyone can go to the login page
//            .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
            .logout()
                .logoutSuccessUrl("/") // append a query string value
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
//            .authorizeHttpRequests()
//                .requestMatchers( "/profile","/clients/dashboard", "/guest_listManager","/likedVendors", "/budget_tracker", "/ideaboard")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER")
//            .and()
//            .authorizeHttpRequests()
//                .requestMatchers("/vendor/profile")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_VENDOR")
//            .and()
//            .authorizeHttpRequests()
//                .requestMatchers("/","/aboutus","/vendors","/info/**", "/client/registration", "/vendor/registration", "/register/verify", "/vendors/categories/*","/vendors/individual/*", "/login", "/sign-up", "/js/**","/img/**", "/css/**", "/error")
//                .permitAll()
//            .and()
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/vendor/profile")
                    .hasRole("VENDOR")
                    .requestMatchers("/profile", "/clients/dashboard", "/guest_listManager","/likedVendors", "/budget_tracker", "/ideaboard")
                    .hasRole("CUSTOMER")
                    // .requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("DBA")))
                    .requestMatchers( "/", "/aboutus","/vendors","/info/**", "/client/registration", "/vendor/registration", "/register/verify", "/vendors/categories/*","/vendors/individual/*", "/login", "/sign-up", "/js/**","/img/**", "/css/**", "/error")
                    .permitAll()
                    .anyRequest()
                    .permitAll()
//                    .denyAll()
                );
        return http.build();
    }

}

