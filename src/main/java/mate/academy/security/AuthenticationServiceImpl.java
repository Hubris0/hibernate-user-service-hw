package mate.academy.security;

import java.util.Optional;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + " already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        boolean isValid = userOptional.map(user -> user
                .getPassword()
                .equals(HashUtil
                        .hashPassword(password, user
                                .getSalt())))
                .orElse(false);

        if (!isValid) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return userOptional.get();
    }
}
