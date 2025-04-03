package dev.javarush.taming_thymeleaf.thyme_wizards.team.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.web.EditMode;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamId;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamNotFoundException;
import dev.javarush.taming_thymeleaf.thyme_wizards.team.TeamService;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

  public TeamController(TeamService teamService, UserService userService) {
    this.teamService = teamService;
    this.userService = userService;
  }

  @GetMapping
  public String index(Model model, @SortDefault.SortDefaults(@SortDefault("name")) Pageable pageable) {
    model.addAttribute("teams", teamService.getTeams(pageable));
    return "teams/index";
  }

  @GetMapping("/create")
  @Secured("ROLE_ADMIN")
  public String createTeamForm(Model model) {
    model.addAttribute("editMode", EditMode.CREATE);
    model.addAttribute("team", new CreateTeamFormData());
    model.addAttribute("users", userService.getAllUsersNameAndId());
    return "teams/edit";
  }

  @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public String doCreateTeam(@Validated @ModelAttribute("team") CreateTeamFormData formData,
                               BindingResult bindingResult, Model model) {
      if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.CREATE);
          model.addAttribute("users", userService.getAllUsersNameAndId());
          return "teams/edit";
      }
        teamService.createTeam(formData.getName(), formData.getCoachId());
        return "redirect:/teams";
    }

    @GetMapping("/{id}/edit")
    @Secured("ROLE_ADMIN")
    public String editTeamForm(@PathVariable("id") TeamId id, Model model) {
        var team = teamService.getTeam(id)
            .orElseThrow(() -> new TeamNotFoundException(id));
        model.addAttribute("editMode", EditMode.UPDATE);
        model.addAttribute("team", EditTeamFormData.fromTeam(team));
        model.addAttribute("users", userService.getAllUsersNameAndId());
        return "teams/edit";
    }

    @PostMapping("/{id}/edit")
    @Secured("ROLE_ADMIN")
    public String doEditTeam(
        @PathVariable("id") TeamId id,
        @Validated @ModelAttribute("team") EditTeamFormData formData,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.UPDATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            return "teams/edit";
        }
        teamService.editTeam(
            id,
            formData.getName(),
            formData.getVersion(),
            formData.getCoachId()
        );
        return "redirect:/teams";
    }

    @PostMapping("{id}/delete")
    @Secured("ROLE_ADMIN")
    public String doDeleteTeam(@PathVariable("id") TeamId id, RedirectAttributes redirectAttributes) {
      var team = teamService.getTeam(id)
            .orElseThrow(() -> new TeamNotFoundException(id));
      teamService.deleteTeam(id);
      redirectAttributes.addFlashAttribute("deletedTeamName", team.getName());
        return "redirect:/teams";
    }

}
