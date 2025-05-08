package com.healthcare.healthapp.config;

import com.healthcare.healthapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @SuppressWarnings({ "removal", "deprecation" })
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(auth -> auth
                .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()  // Ensure login page and assets are accessible
                .anyRequest().authenticated()  // Protect all other pages
            )
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page URL
                .successHandler(loginSuccessHandler)  // Redirect by role after successful login
                .permitAll()  // Allow all users to access login page
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Specify logout URL explicitly (default is /logout)
                .logoutSuccessUrl("/login?logout")  // Redirect to login page after successful logout
                .permitAll()  // Allow all users to logout
            )
            .userDetailsService(customUserDetailsService)  // Custom UserDetailsService
            .csrf().disable();  // Disabling CSRF for simplicity (ensure it's properly handled for production)

        return http.build();
    }

    // Bean for encoding passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager for auto-login use
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
