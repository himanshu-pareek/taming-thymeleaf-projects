package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {
  private final PasswordEncoder passwordEncoder;

  public WebSecurityConfiguration(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
        .username("user")
        .password(this.passwordEncoder.encode("secure"))
        .roles("USER")
        .build();
    UserDetails admin = User.builder()
        .username("admin")
        .password(this.passwordEncoder.encode("moresecure"))
        .roles("USER", "ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        authz -> authz.requestMatchers("/users/create").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/users", "/users/*").hasRole("USER")
            .requestMatchers("/users/**").hasRole("ADMIN")
            .anyRequest().authenticated()
    )
        .formLogin(Customizer.withDefaults())
        .logout(Customizer.withDefaults());
    return http.build();
  }
}
