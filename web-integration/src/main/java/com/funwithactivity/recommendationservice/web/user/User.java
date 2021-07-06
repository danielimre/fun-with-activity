package com.funwithactivity.recommendationservice.web.user;

import java.util.Collection;
import java.util.List;

import com.funwithactivity.recommendationservice.person.Person;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Dummy user class for demonstration purposes.
 * Should be coming from user profile domain.
 */
@Builder
@EqualsAndHashCode
@ToString
public final class User implements UserDetails {
    @NonNull
    private final String username;
    @NonNull
    @ToString.Exclude
    private final String password;
    @NonNull
    @Getter
    private final Person details;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
