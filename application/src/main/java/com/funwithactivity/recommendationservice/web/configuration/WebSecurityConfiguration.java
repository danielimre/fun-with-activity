package com.funwithactivity.recommendationservice.web.configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.funwithactivity.recommendationservice.person.Height;
import com.funwithactivity.recommendationservice.person.Mass;
import com.funwithactivity.recommendationservice.person.Person;
import com.funwithactivity.recommendationservice.web.security.DummyUserDetailsService;
import com.funwithactivity.recommendationservice.web.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/actuator/*", "/api-docs", "/swagger-ui/*", "/v3/*", "/example/process").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        User user1 = User.builder()
            .username("user")
            .password("pass")
            .details(Person.builder()
                .height(new Height(BigDecimal.valueOf(184)))
                .weight(new Mass(BigDecimal.valueOf(74)))
                .birthDate(LocalDate.of(1981, 10, 12))
                .build())
            .build();
        User user2 = User.builder()
            .username("user2")
            .password("pass")
            .details(Person.builder()
                .height(new Height(BigDecimal.valueOf(175)))
                .weight(new Mass(BigDecimal.valueOf(81)))
                .birthDate(LocalDate.of(1992, 3, 12))
                .build())
            .build();
        return new DummyUserDetailsService(user1, user2);
    }
}
