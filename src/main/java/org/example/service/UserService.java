package org.example.service;

import org.example.annotation.Controller;
import org.example.annotation.Inject;

@Controller
public class UserService {
    private final UserService userService;


    @Inject
    public UserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
