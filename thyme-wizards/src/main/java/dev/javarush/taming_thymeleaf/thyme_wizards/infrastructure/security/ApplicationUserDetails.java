package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUserDetails implements UserDetails {
  private final UserId id;
  private final String username;
  private final String displayName;
  private final String password;
  private final Set<GrantedAuthority> authorities;

  public ApplicationUserDetails(User user) {
    this.id = user.getId();
    this.username = user.getEmail().asString();
    this.displayName = user.getUsername().getFullName();
    this.password = user.getPassword();
    this.authorities = user.getRoles()
        .stream()
        .map(Enum::name)
        .map(role -> "ROLE_" + role)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
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

  public UserId getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }
}
