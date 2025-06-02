package com.example.spring_boot_thymeleaf;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;        
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.SQLException;
import java.util.Collections;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        try {
            boolean valid = LoginHandler.authenticateUser(email, rawPassword);
            if (!valid) {
                throw new BadCredentialsException("Invalid credentials");
            }
            UserDetails user = new User(
                email,
                "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            return new UsernamePasswordAuthenticationToken(user, rawPassword, user.getAuthorities());
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new AuthenticationServiceException("Error accessing database", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
