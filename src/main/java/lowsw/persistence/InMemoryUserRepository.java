package lowsw.persistence;

import java.util.HashMap;
import java.util.Map;

import lowsw.domain.User;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();
    private long nextId = 1;

    @Override
    public User create(String username, String passwordHash) {
        if (users.containsKey(username)) throw new IllegalStateException("Duplicate username");
        User user = new User(nextId++, username, passwordHash);
        users.put(username, user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }
}
