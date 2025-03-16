package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(CreateUserParameters parameters) {
        LOGGER.debug(
                "Creating user {} ({})",
                parameters.username().getFullName(),
                parameters.email().asString());
        UserId userId = this.userRepository.nextId();
        String encodedPassword = passwordEncoder.encode(parameters.password());
        User user = User.createUser(
                userId,
                parameters.username(),
                parameters.gender(),
                parameters.birthday(),
                parameters.email(),
                parameters.phoneNumber(),
                encodedPassword);
        return this.userRepository.save(user);
    }

    @Override
    public User createAdministrator(CreateUserParameters parameters) {
        LOGGER.debug(
                "Creating administrator {} ({})",
                parameters.username().getFullName(),
                parameters.email().asString());
        UserId userId = this.userRepository.nextId();
        String encodedPassword = passwordEncoder.encode(parameters.password());
        User user = User.createAdministrator(
                userId,
                parameters.username(),
                parameters.gender(),
                parameters.birthday(),
                parameters.email(),
                parameters.phoneNumber(),
                encodedPassword);
        return this.userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean userWithEmailExists(Email email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public User editUser(UserId userId, EditUserParameters parameters) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (parameters.version() != user.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(
                    User.class,
                    user.getId().toString());
        }

        parameters.update(user);
        return user;
    }

    @Override
    public User getUser(UserId userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public Optional<User> getUserUsingEmail(String email) {
        return this.userRepository.findUserByEmail(new Email(email));
    }

    @Override
    public void deleteUser(UserId userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }

    @Override
    public long countUsers() {
        return this.userRepository.count();
    }
}
