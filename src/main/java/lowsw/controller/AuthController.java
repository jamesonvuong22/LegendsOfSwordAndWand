package lowsw.controller;

import lowsw.domain.User;
import lowsw.service.AuthService;

public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }
    public User register(String username, String password) { return authService.register(username, password); }
    public User login(String username, String password) { return authService.login(username, password); }
}

