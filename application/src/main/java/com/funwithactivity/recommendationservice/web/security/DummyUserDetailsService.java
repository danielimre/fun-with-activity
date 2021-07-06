package com.funwithactivity.recommendationservice.web.security;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.funwithactivity.recommendationservice.web.user.User;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Dummy user details service to store extended users in-memory.
 */
public final class DummyUserDetailsService implements UserDetailsService {
    private final Map<String, User> userStore;

    public DummyUserDetailsService(@NonNull User... users) {
        userStore = Arrays.stream(users).collect(toMap(User::getUsername, u -> u));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userStore.get(username)).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
