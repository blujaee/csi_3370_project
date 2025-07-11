package com.example.spring_boot_thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.SQLException;
import java.util.Collections;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserFetcher uf;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException 
    {
        System.out.println("[AUTH] >>> CustomAuthenticationProvider.authenticate() called");
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        try {
            boolean valid = LoginHandler.authenticateUser(email, rawPassword);
            if (!valid) {
                throw new BadCredentialsException("Invalid credentials");
            } else {
                System.out.println("[AUTH] >>> LoginHandler returned valid.");
            }

            // Set role of user to PATIENT or MEDICAL_STAFF
            UserInfo userInfo = uf.getUserByEmail(email);
            String role = userInfo.getRole();
            
            if (role.equals("PATIENT")) {
                role = "ROLE_PATIENT";
            } else if (role.equals("MEDICAL STAFF")) {
                role = "ROLE_MEDICAL_STAFF";
            } else {
                // FALLBACK CASE IF NO ROLE IS FOUND
                role = "ROLE_USER";
            }

            UserDetails user = new User(
                email,
                "",
                Collections.singletonList(new SimpleGrantedAuthority(role))
            );

            return new UsernamePasswordAuthenticationToken(user, rawPassword, user.getAuthorities());
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new AuthenticationServiceException("Database error", e);
        } catch (Exception e) {
            System.out.println("[ERROR] >>>> Unexpected exception in authenticate(): " + e.getMessage());
            e.printStackTrace();
            throw new AuthenticationServiceException("[ERROR] >>>> ", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
