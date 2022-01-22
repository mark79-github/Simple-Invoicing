package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.bind.UserProfileBindingModel;
import bg.softuni.invoice.model.bind.UserRegisterBindingModel;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.service.RoleServiceModel;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.model.view.UserViewModel;
import bg.softuni.invoice.service.UserService;
import bg.softuni.invoice.web.annotation.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.ErrorMsg.USERNAME_NOT_FOUND;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String USER_REGISTER_BINDING_MODEL = "userRegisterBindingModel";
    private static final String USER_PROFILE_BINDING_MODEL = "userProfileBindingModel";
    private static final String VALIDATION_BINDING_RESULT = "org.springframework.validation.BindingResult.";
    private static final String REDIRECT_USER_ALL = "redirect:/user/all";
    private static final String REDIRECT_REGISTER = "redirect:register";
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PageTitle("User register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model) {

        if (!model.containsAttribute(USER_REGISTER_BINDING_MODEL)) {
            model.addAttribute(USER_REGISTER_BINDING_MODEL, new UserRegisterBindingModel());
        }

        return "user/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid
                                      @ModelAttribute(name = USER_REGISTER_BINDING_MODEL) UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(USER_REGISTER_BINDING_MODEL, userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + USER_REGISTER_BINDING_MODEL, bindingResult);

            return REDIRECT_REGISTER;
        }

        boolean present = this.userService.getUserByName(userRegisterBindingModel.getUsername()).isPresent();

        if (present) {
            redirectAttributes.addFlashAttribute(USER_REGISTER_BINDING_MODEL, userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + USER_REGISTER_BINDING_MODEL, bindingResult);
            bindingResult.rejectValue("username", "error.userRegisterBindingModel", "Username is already taken");

            return REDIRECT_REGISTER;
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute(USER_REGISTER_BINDING_MODEL, userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + USER_REGISTER_BINDING_MODEL, bindingResult);
            bindingResult.rejectValue("confirmPassword", "error.userRegisterBindingModel", "Confirm password did not match");

            return REDIRECT_REGISTER;
        }

        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:login";
    }

    @GetMapping("/login")
    @PageTitle("User login")
    @PreAuthorize("isAnonymous()")
    public String login() {
        return "user/login";
    }

    @GetMapping("/profile{id}")
    @PageTitle("User profile")
    @PreAuthorize("isAuthenticated() && #id eq principal.username")
    public String profile(@RequestParam(name = "id") String id,
                          @AuthenticationPrincipal User principal,
                          Model model) {

        UserServiceModel userServiceModel = this.userService.getUserByName(id).orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, id)));

        if (!model.containsAttribute(USER_PROFILE_BINDING_MODEL)) {
            UserProfileBindingModel userProfileBindingModel = this.modelMapper.map(userServiceModel, UserProfileBindingModel.class);
            model.addAttribute(USER_PROFILE_BINDING_MODEL, userProfileBindingModel);
        }

        return "user/profile";
    }

    @PostMapping("/profile{id}")
    @PreAuthorize("isAuthenticated() && #id eq principal.username")
    public String profileConfirm(@Valid
                                     @ModelAttribute(name = USER_PROFILE_BINDING_MODEL) UserProfileBindingModel userProfileBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal User principal,
                                 @RequestParam(name = "id") String id,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(USER_PROFILE_BINDING_MODEL, userProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileBindingModel", bindingResult);
            redirectAttributes.addAttribute("id", id);

            return "redirect:profile";
        }

        UserServiceModel userServiceModel = this.userService.getUserByName(id).orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, id)));

        userServiceModel.setFirstName(userProfileBindingModel.getFirstName());
        userServiceModel.setLastName(userProfileBindingModel.getLastName());

        this.userService.editUserData(userServiceModel);

        model.addAttribute("user", this.userService.loadUserByUsername(principal.getUsername()));

        return "redirect:/home";
    }

    @GetMapping("/all")
    @PageTitle("User all")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String allUsers(Model model) {

        List<UserViewModel> users = this.userService.getAllUsers()
                .stream()
                .map(userServiceModel -> {
                    UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
                    Set<String> authorities = userServiceModel.getAuthorities()
                            .stream()
                            .map(RoleServiceModel::getAuthority)
                            .collect(Collectors.toSet());
                    userViewModel.setAuthorities(authorities);
                    return userViewModel;
                })
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("comparator", Comparator.comparing(UserViewModel::getUsername));

        return "user/all";
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setAdminRole(@PathVariable String id) {
        this.userService.setAdmin(id);

        return REDIRECT_USER_ALL;
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setUserRole(@PathVariable String id) {
        this.userService.setUser(id);

        return REDIRECT_USER_ALL;
    }

    @PostMapping("/set-enabled/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setEnabled(@PathVariable String id) {
        this.userService.setUserEnabled(id);

        return REDIRECT_USER_ALL;
    }

    @PostMapping("/set-disabled/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setDisabled(@PathVariable String id) {
        this.userService.setUserDisabled(id);

        return REDIRECT_USER_ALL;
    }

}
