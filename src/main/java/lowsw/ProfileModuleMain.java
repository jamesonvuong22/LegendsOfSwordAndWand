package lowsw;

import lowsw.domain.User;
import lowsw.persistence.InMemoryUserRepository;
import lowsw.service.AuthService;

// With the use of AI
public class ProfileModuleMain {
    public static void main(String[] args) {
        AuthService authService = new AuthService(new InMemoryUserRepository());
        User user = authService.register("jameson", "secret");
        System.out.println("Registered: " + user.getUsername());
        System.out.println("Login success? " + (authService.login("jameson", "secret") != null));
    }
}
