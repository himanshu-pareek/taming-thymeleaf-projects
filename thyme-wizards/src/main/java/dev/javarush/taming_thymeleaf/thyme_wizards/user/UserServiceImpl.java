package dev.javarush.taming_thymeleaf.thyme_wizards.user;

import java.util.Optional;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
                parameters.username(),
                parameters.gender(),
                parameters.birthday(),
                parameters.email(),
                parameters.phoneNumber()
        );
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
                user.getId().toString()
            );
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
}
