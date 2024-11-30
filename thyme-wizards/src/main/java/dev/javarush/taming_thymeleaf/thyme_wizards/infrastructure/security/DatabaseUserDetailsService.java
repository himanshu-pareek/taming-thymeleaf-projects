package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security;

import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DatabaseUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public DatabaseUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findUserByEmail(new Email(username))
        .orElseThrow(
            () -> new UsernameNotFoundException("User with email " + username + " not found"));
    return new ApplicationUserDetails(user);
  }
}
