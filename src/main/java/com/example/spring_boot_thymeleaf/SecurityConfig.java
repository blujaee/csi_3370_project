package com.example.spring_boot_thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Allows for authorizing method invocations using USER role
// For a GET request, adding @PreAuthorize("!hasRole('MEDICAL_STAFF')")
// Will RESTRICT the page from any USERs with MEDICAL_STAFF role
// See WebController.java search() for one example
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    // 1) Expose a PasswordEncoder bean (BCrypt) if you need it elsewhere
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Expose your CustomAuthenticationProvider as a bean
    @Bean
    public AuthenticationProvider customAuthProvider() {
        return new CustomAuthenticationProvider();
    }

    // 3) Register both the HttpSecurity rules and the provider
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          // register your provider here
          .authenticationProvider(customAuthProvider())

          // authorization rules
          .authorizeHttpRequests(auth -> auth
              .requestMatchers(
                  "/login",
                  "/register",
                  "/css/**", "/js/**", "/images/**"
              ).permitAll()
              .anyRequest().authenticated()
          )

          // form‐login config
          .formLogin(form -> form
              .loginPage("/login")
              .loginProcessingUrl("/login")
              .failureUrl("/login?error")
              .defaultSuccessUrl("/", /*alwaysUse=*/ true)
              .permitAll()
          )

          // logout config
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/login?logout")
              .permitAll()
          );

        return http.build();
    }
}
