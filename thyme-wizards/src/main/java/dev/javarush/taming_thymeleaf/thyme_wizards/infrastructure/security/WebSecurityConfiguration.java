package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  public WebSecurityConfiguration(PasswordEncoder passwordEncoder,
                                  UserDetailsService userDetailsService) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return authenticationProvider;
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    UserDetails user = User.builder()
//        .username("user")
//        .password(this.passwordEncoder.encode("secure"))
//        .roles("USER")
//        .build();
//    UserDetails admin = User.builder()
//        .username("admin")
//        .password(this.passwordEncoder.encode("moresecure"))
//        .roles("USER", "ADMIN")
//        .build();
//    return new InMemoryUserDetailsManager(user, admin);
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        authz -> authz.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/svg/*").permitAll()
            .anyRequest().authenticated()
    )
        .formLogin(
            formLogin -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        )
        .logout(LogoutConfigurer::permitAll);
    return http.build();
  }
}
