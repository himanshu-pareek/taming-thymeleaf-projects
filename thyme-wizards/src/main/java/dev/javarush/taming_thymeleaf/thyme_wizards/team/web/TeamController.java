package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @GetMapping
    public String index() {
        return "teams/index";
    }

}
