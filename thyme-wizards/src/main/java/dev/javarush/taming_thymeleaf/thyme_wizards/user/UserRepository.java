package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import com.google.common.collect.ImmutableSortedSet;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends
    CrudRepository<User, UserId>,
    PagingAndSortingRepository<User, UserId>,
    UserRepositoryCustom {
  Optional<User> findUserByEmail(Email email);

  @Query("select new dev.javarush.taming_thymeleaf.thyme_wizards.user.UserNameAndId(u.username, u.id) from User u")
  Iterable<UserNameAndId> findAllUsersNameAndId();
}
