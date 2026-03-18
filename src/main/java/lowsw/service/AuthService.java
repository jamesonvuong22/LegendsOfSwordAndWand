package lowsw.service;

import lowsw.domain.User;
import lowsw.persistence.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User register(String username, String password) {
        return userRepository.create(username, hash(password));
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        return user.getPasswordHash().equals(hash(password)) ? user : null;
    }

    private String hash(String password) { return "HASH::" + password; }
}
