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

@Controller
@RequestMapping("/user")
public class UserController {

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

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "user/register";
    }

    @PostMapping("/register")
//    @PageTitle("Post mapping - register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid
                                  @ModelAttribute(name = "userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:register";
        }

        boolean present = this.userService.getUserByName(userRegisterBindingModel.getUsername()).isPresent();

        if (present) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            bindingResult.rejectValue("username", "error.userRegisterBindingModel", "Username is already taken");

            return "redirect:register";
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            bindingResult.rejectValue("confirmPassword", "error.userRegisterBindingModel", "Confirm password did not match");

            return "redirect:register";
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

    @GetMapping("/profile/{id}")
    @PageTitle("User profile")
    @PreAuthorize("isAuthenticated() && #id eq principal.id")
    public String profile(@PathVariable("id") String id,
                          @AuthenticationPrincipal User principal,
                          Model model) {

        UserServiceModel userServiceModel = this.userService.getUserById(id);

        if (!model.containsAttribute("userProfileBindingModel")) {
            UserProfileBindingModel userProfileBindingModel = this.modelMapper.map(userServiceModel, UserProfileBindingModel.class);
            model.addAttribute("userProfileBindingModel", userProfileBindingModel);
        }

        return "user/profile";
    }

    @PostMapping("/profile/{id}")
//    @PageTitle("Post mapping - profile")
    @PreAuthorize("isAuthenticated() && #id eq principal.id")
    public String profileConfirm(@Valid
                                 @ModelAttribute(name = "userProfileBindingModel") UserProfileBindingModel userProfileBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal User principal,
                                 @PathVariable("id") String id,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userProfileBindingModel", userProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileBindingModel", bindingResult);

            return "redirect:{id}";
        }

        UserServiceModel userServiceModel = this.userService.getUserById(id);

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
//    @PageTitle("Post mapping - set-admin")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setAdminRole(@PathVariable String id) {
        this.userService.setAdmin(id);

        return "redirect:/user/all";
    }

    @PostMapping("/set-user/{id}")
//    @PageTitle("Post mapping - set-user")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setUserRole(@PathVariable String id) {
        this.userService.setUser(id);

        return "redirect:/user/all";
    }

    @PostMapping("/set-enabled/{id}")
//    @PageTitle("Post mapping - set-enabled")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setEnabled(@PathVariable String id) {
        this.userService.setUserEnabled(id);

        return "redirect:/user/all";
    }

    @PostMapping("/set-disabled/{id}")
//    @PageTitle("Post mapping - set-disabled")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setDisabled(@PathVariable String id) {
        this.userService.setUserDisabled(id);

        return "redirect:/user/all";
    }

}
