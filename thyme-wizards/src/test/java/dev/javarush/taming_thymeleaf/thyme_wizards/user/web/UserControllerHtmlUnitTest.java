package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gargoylesoftware.htmlunit.html.HtmlEmailInput;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.CreateUserParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security.StubUserDetailsService;
import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.security.WebSecurityConfiguration;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.UserService;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Users;

@WebMvcTest(UserController.class)
public class UserControllerHtmlUnitTest {

    @Autowired
    private WebClient webClient;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Test
    @WithUserDetails(StubUserDetailsService.USERNAME_ADMIN)
    void testGetUsersAsAdmin() throws Exception {
        when(userService.getAllUsers(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of("Kaden Whyte", "Charlten Faulkner", "Yuvaan Mcpherson")
                        .stream()
                        .map(name -> name.split(" "))
                        .map(nameParts -> new Username(nameParts[0], nameParts[1]))
                        .map(username -> Users.createUser(username))
                        .toList()));

        HtmlPage htmlPage = webClient.getPage("/users");
        assertEquals("Taming Thymeleaf - Users", htmlPage.getTitleText());
        DomNodeList<DomElement> h1Headers = htmlPage.getElementsByTagName("h1");
        assertThat(h1Headers).hasSize(1)
                .element(0)
                .extracting(DomElement::asNormalizedText)
                .isEqualTo("Users");

        HtmlTable usersTable = htmlPage.getHtmlElementById("users-table");
        var rows = usersTable.getRows();
        assertThat(rows).hasSize(4);
        var headerRow = rows.get(0);
        assertThat(headerRow.getCell(0).asNormalizedText()).isEqualTo("Name");
        assertThat(headerRow.getCell(1).asNormalizedText()).isEqualTo("Gender");
        assertThat(headerRow.getCell(2).asNormalizedText()).isEqualTo("Birth Day");
        assertThat(headerRow.getCell(3).asNormalizedText()).isEqualTo("Email");

        var firstUserRow = rows.get(1);
        assertThat(firstUserRow.getCell(0).asNormalizedText()).isEqualTo("Kaden Whyte");
        assertThat(firstUserRow.getCell(1).asNormalizedText()).isEqualTo("MALE");
        assertThat(firstUserRow.getCell(2).asNormalizedText()).isEqualTo("1997-01-01");
        assertThat(firstUserRow.getCell(3).asNormalizedText()).isEqualTo("kaden.whyte@javarush.dev");
    }

    @Test
    @WithUserDetails(StubUserDetailsService.USERNAME_ADMIN)
    void testAddUser() throws Exception {
        when(userService.getAllUsers(any(Pageable.class)))
                .thenReturn(Page.empty());

        HtmlPage htmlPage = webClient.getPage("/users");
        DomNodeList<DomElement> anchorTags = htmlPage.getElementsByTagName("a");
        // anchorTags.forEach(a -> System.out.println(a.asNormalizedText()));
        Optional<DomElement> addUserLink = anchorTags.stream()
                .filter(a -> a.asNormalizedText().equals("Create user"))
                .findFirst();
        assertThat(addUserLink).isPresent();
        HtmlPage addUserPage = addUserLink.get().click();

        assertThat(addUserPage.getTitleText()).isEqualTo("Taming Thymeleaf - Add user");
        DomNodeList<DomElement> h1Headers = addUserPage.getElementsByTagName("h1");
        assertThat(h1Headers).hasSize(1)
                .element(0)
                .extracting(DomElement::asNormalizedText)
                .isEqualTo("Create user");

        addUserPage.getElementById("gender-MALE").click();
        addUserPage.<HtmlTextInput>getElementByName("firstName").setText("John");
        addUserPage.<HtmlTextInput>getElementByName("lastName").setText("Miller");
        addUserPage.<HtmlEmailInput>getElementByName("email").setText("john.miller@javarush.dev");
        addUserPage.<HtmlTextInput>getElementByName("birthday").setText("1990-01-01");
        addUserPage.<HtmlPasswordInput>getElementByName("password").setText("verysecure");
        addUserPage.<HtmlPasswordInput>getElementByName("passwordRepeated").setText("verysecure");
        addUserPage.<HtmlTextInput>getElementByName("phoneNumber").setText("+1234567890");

        HtmlPage pageAfterFormSubmit = addUserPage.getElementById("submit-button").click();
//        assertThat(pageAfterFormSubmit.getUrl()).isEqualTo(new URL("http://localhost:8080/users"));
//
//        ArgumentCaptor<CreateUserParameters> captor = ArgumentCaptor.forClass(CreateUserParameters.class);
//        verify(userService).createUser(captor.capture());
//
//        CreateUserParameters parameters = captor.getValue();
//        assertThat(parameters.username().getFirstName()).isEqualTo("John");
//        assertThat(parameters.username().getLastName()).isEqualTo("Miller");
//        assertThat(parameters.email()).isEqualTo(new Email("john.miller@javarush.dev"));
//        assertThat(parameters.birthday()).isEqualTo(LocalDate.parse("1990-01-01"));
//        assertThat(parameters.password()).isEqualTo("verysecure");
//        assertThat(parameters.phoneNumber()).isEqualTo(new PhoneNumber("+1234567890"));
    }

    @TestConfiguration
    @Import(WebSecurityConfiguration.class)
    static class TestConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        ITemplateResolver svgTemplateResolver() {
            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
            resolver.setPrefix("classpath:/templates/svg/");
            resolver.setSuffix(".svg");
            resolver.setTemplateMode("XML");
            resolver.setCharacterEncoding("UTF-8");
            resolver.setCacheable(false);
            return resolver;
        }

        @Bean
        UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
            return new StubUserDetailsService(passwordEncoder);
        }
    }

}
