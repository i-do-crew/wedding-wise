package com.idocrew.weddingwise.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .loginPage("/login")
                .defaultSuccessUrl("/clients/dashboard") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
            .and()
            .logout()
                .logoutSuccessUrl("/") // append a query string value
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
            .authorizeHttpRequests(request -> request
                .requestMatchers("/profile","/clients/dashboard", "/guest_listManager","/likedVendors", "/budget_tracker")
                .authenticated()
                .requestMatchers("/","/aboutus","/vendors","/info/budget", "/info/guests", "/info/ideas", "/info/vendors","/client/registration", "/vendor/registration", "/vendors/categories/*","/vendors/individual/*", "/login", "/sign-up", "/js/**","/img/**", "/css/**")
                .permitAll()
            );
        return http.build();
    }

}

