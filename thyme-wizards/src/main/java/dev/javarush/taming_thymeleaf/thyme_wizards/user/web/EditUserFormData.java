package dev.javarush.taming_thymeleaf.thyme_wizards.user.web;

import dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.validation.ValidationGroupThree;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.EditUserParameters;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Email;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.PhoneNumber;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.User;
import dev.javarush.taming_thymeleaf.thyme_wizards.user.Username;
import java.util.Base64;

@HasOwnEmail(groups = ValidationGroupThree.class)
public class EditUserFormData extends UserFormData {
  private String id;
  private long version;
  private String avatarBase64Encoded;

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

    if (user.getAvatar() != null) {
      String encoded = Base64.getEncoder().encodeToString(user.getAvatar());
        formData.setAvatarBase64Encoded(encoded);
    }
    return formData;
  }

  public EditUserParameters toEditUserParameters() {
    return new EditUserParameters(
        new Username(getFirstName(), getLastName()),
        getGender(),
        getBirthday(),
        new Email(getEmail()),
        new PhoneNumber(getPhoneNumber()),
        getAvatarFile(),
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

  public String getAvatarBase64Encoded() {
    return avatarBase64Encoded;
  }

  public void setAvatarBase64Encoded(String avatarBase64Encoded) {
    this.avatarBase64Encoded = avatarBase64Encoded;
  }
}
