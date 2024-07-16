package hr.dice.luka_jurkic.mvc.controller;

import hr.dice.luka_jurkic.mapper.UserMapper;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;
import hr.dice.luka_jurkic.service.CommentService;
import hr.dice.luka_jurkic.service.RecipeService;
import hr.dice.luka_jurkic.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserMVController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CommentService commentService;
    private final RecipeService recipeService;

    public UserMVController(UserService userService, UserMapper userMapper, CommentService commentService, RecipeService recipeService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.commentService = commentService;
        this.recipeService = recipeService;
    }

    @GetMapping("/details")
    public String getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userMapper.detailDTOToRequest(userService.getUserDetails(authentication.getName())));
        model.addAttribute("userRole", userService.getUserDetails(authentication.getName()).getRole());
        model.addAttribute("roles", userService.getUserRoles());
        model.addAttribute("comments", commentService.getCommentsContentByUser(authentication.getName()));
        return "user/show_userDetails";
    }

    @GetMapping
    public String getUser(@RequestParam(name = "username") String username, Model model) {
        UserDTO user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("recipes", recipeService.getRecipesByUser(username));
        model.addAttribute("comments", commentService.getCommentsByUser(username));
        return "user/show_user";
    }

    @GetMapping("/all")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list_users";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        UserRequest request = new UserRequest();
        model.addAttribute("request", request);
        return "user/form_createUser";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute(name = "request") UserRequest request, BindingResult bindingResult,
                             Model model) {
        if(!userService.isUnique(request.getUsername())){
            bindingResult.rejectValue("username", "error.request", "Username taken");
        }
        if(!userService.isValidPassword(request.getPassword())) {
            bindingResult.rejectValue("password", "error.request", "Password is invalid");
        }
        if (bindingResult.hasErrors()) {
            return "user/form_createUser";
        }
        userService.createUser(request);
        model.addAttribute("users", userService.getUsers());
        return "login";
    }

    @GetMapping("/delete")
    public String deleteUserForm() {
        return "user/form_deleteUser";
    }

    @PostMapping("/delete")
    public String deleteUser(HttpServletRequest request) throws ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        request.logout();
        userService.deleteUser(username);
        return "login";
    }

    @PostMapping("/update")
    public String updateUser(
            HttpServletRequest httpRequest,
            @Valid @ModelAttribute(name = "user") UserRequest request,
            BindingResult bindingResult,
            Model model) throws ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!userService.isUnique(request.getUsername()) && !Objects.equals(request.getUsername(), authentication.getName())){
            bindingResult.rejectValue("username", "error.request", "Username taken");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", userService.getUserRoles());
            model.addAttribute("comments", commentService.getCommentsContentByUser(authentication.getName()));
            return "user/show_userDetails";
        }
        userService.updateUser(authentication.getName(), request);
        httpRequest.logout();
        return "login";
    }

    @PostMapping("/upgrade")
    public String upgradeUser(@RequestParam(name = "username") String username, Model model) {
        userService.upgradeUserRights(username);
        return getUsers(model);
    }

    @PostMapping("/downgrade")
    public String downgrade(HttpServletRequest httpRequest) throws ServletException {
        userService.downgradeUserRights();
        httpRequest.logout();
        return "login";
    }

}
