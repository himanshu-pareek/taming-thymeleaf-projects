package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
    return new InMemoryUserDetailsManager(user);
  }
}
