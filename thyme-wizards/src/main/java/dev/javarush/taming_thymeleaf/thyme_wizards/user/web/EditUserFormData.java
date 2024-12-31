package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupThree;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.EditUserParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;

@HasOwnEmail(groups = ValidationGroupThree.class)
public class EditUserFormData extends UserFormData {
  private String id;
  private long version;

  public static EditUserFormData fromUser(User user) {
    var formData = new EditUserFormData();
    formData.setId(user.getId().asString());
    formData.setVersion(user.getVersion());
    formData.setFirstName(user.getUsername().getFirstName());
    formData.setLastName(user.getUsername().getLastName());
    formData.setEmail(user.getEmail().asString());
    formData.setBirthday(user.getBirthday());
    formData.setGender(user.getGender());
    formData.setPhoneNumber(user.getPhoneNumber().asString());
    return formData;
  }

  public EditUserParameters toEditUserParameters() {
    return new EditUserParameters(
        new Username(getFirstName(), getLastName()),
        getGender(),
        getBirthday(),
        new Email(getEmail()),
        new PhoneNumber(getPhoneNumber()),
        version);
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public long getVersion() {
    return version;
  }
}
