package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserParameters parameters) {
        UserId userId = this.userRepository.nextId();
        User user = new User(
                userId,
                parameters.getUsername(),
                parameters.getGender(),
                parameters.getBirthday(),
                parameters.getEmail(),
                parameters.getPhoneNumber()
        );
        return this.userRepository.save(user);
    }

    @Override
    public ImmutableSet<User> getAllUsers() {
        return ImmutableSet.copyOf(userRepository.findAll());
    }
}
