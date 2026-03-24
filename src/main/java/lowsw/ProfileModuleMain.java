package lowsw;

import lowsw.domain.User;
import lowsw.persistence.InMemoryUserRepository;
import lowsw.service.AuthService;

public class ProfileModuleMain {
    public static void main(String[] args) {
        AuthService authService = new AuthService(new InMemoryUserRepository());

        System.out.println("=== Profile Module Demo ===");

        // Register a new user
        User user = authService.register("jameson", "secret");
        System.out.println("Registered user: " + user.getUsername());

        // Successful login
        boolean loginSuccess = authService.login("jameson", "secret") != null;
        System.out.println("Login with correct password: " + loginSuccess);

        // Failed login
        boolean loginFail = authService.login("jameson", "wrongpass") != null;
        System.out.println("Login with wrong password: " + loginFail);

        // Duplicate registration check
        try {
            authService.register("jameson", "anotherpass");
            System.out.println("Duplicate registration allowed: true");
        } catch (Exception e) {
            System.out.println("Duplicate registration allowed: false");
        }
    }
}
