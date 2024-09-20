package com.example.questApp.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends org.springframework.security.authentication.UsernamePasswordAuthenticationToken {

    public JwtAuthenticationToken(UserDetails principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public JwtAuthenticationToken(UserDetails principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public JwtAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    @Override
    public UserDetails getPrincipal() {
        return (UserDetails) super.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }
}
