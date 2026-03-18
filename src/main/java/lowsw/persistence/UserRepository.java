package lowsw.persistence;

import lowsw.domain.User;

public interface UserRepository {
    User create(String username, String passwordHash);
    User findByUsername(String username);
}

