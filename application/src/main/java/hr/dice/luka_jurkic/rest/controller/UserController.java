package hr.dice.luka_jurkic.rest.controller;

import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.dto.UserDetailsDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;
import hr.dice.luka_jurkic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/user/details")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public UserDetailsDTO getUserDetails(Authentication authentication) {
        return userService.getUserDetails(authentication.getName());
    }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/all/details")
    @Secured({"ROLE_ADMIN"})
    public List<UserDetailsDTO> getUsersDetails() {
        return userService.getUsersDetails();
    }

    @PostMapping
    public UserDetailsDTO createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteUser(Authentication authentication) {
        userService.deleteUser(authentication.getName());
    }
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public UserDetailsDTO updateUser(Authentication authentication, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(authentication.getName(), userRequest);
    }
}
