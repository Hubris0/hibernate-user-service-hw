package mate.academy.security;

import mate.academy.model.User;

public interface AuthenticationService {

    User register(String email, String password);

    User login(String email, String password);
}
