package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  @GetMapping("/login")
  public String login(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      return "auth/login";
    }
    return "redirect:/";
  }
}
