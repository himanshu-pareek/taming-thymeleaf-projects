package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.web.EditMode;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Gender;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserId;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserRole;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("users")
public class UserController {

  private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(
            Model model,
            @SortDefault.SortDefaults({
                    @SortDefault("username.lastName"),
                    @SortDefault("username.firstName")
            }) Pageable pageable) {
        model.addAttribute("users", userService.getAllUsers(pageable));
        return "users/index";
    }

    @GetMapping("create")
    @Secured("ROLE_ADMIN")
    public String createUserForm(Model model) {
        model.addAttribute("user", new CreateUserFormData());
        model.addAttribute("editMode", EditMode.CREATE);
        return "users/edit";
    }

    @PostMapping("create")
    @Secured("ROLE_ADMIN")
    public String createUser(
            @Validated(CreateUserValidationGroupSequence.class) @ModelAttribute("user") CreateUserFormData createUserFormData,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }

        userService.createUser(createUserFormData.toCreateUserParameters());
        return "redirect:/users";
    }

    @GetMapping("{id}/edit")
    @Secured("ROLE_ADMIN")
    public String editUserForm(@PathVariable("id") UserId userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", EditUserFormData.fromUser(user));
        model.addAttribute("editMode", EditMode.UPDATE);
        return "users/edit";
    }

    @PostMapping("{id}/edit")
    @Secured("ROLE_ADMIN")
    public String editUser(
            @PathVariable("id") UserId userId,
            @Validated(EditUserValidationGroupSequence.class) @ModelAttribute("user") EditUserFormData formData,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.UPDATE);
            return "users/edit";
        }
        userService.editUser(userId, formData.toEditUserParameters());
        return "redirect:/users";
    }

    @GetMapping("ex")
    public String throwException() {
        throw new RuntimeException("This is a fake exception for testing");
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    public String deleteUser(
            @PathVariable("id") UserId userId,
            RedirectAttributes redirectAttributes) {
        User user = userService.getUser(userId);
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("deletedUserName", user.getUsername().getFullName());
        return "redirect:/users";
    }

    @ModelAttribute("genders")
    public List<Gender> genders() {
        return List.of(Gender.MALE, Gender.FEMALE, Gender.OTHER);
    }

    @ModelAttribute("possibleRoles")
    public List<UserRole> possibleRoles() {
        return List.of(UserRole.values());
    }
}
