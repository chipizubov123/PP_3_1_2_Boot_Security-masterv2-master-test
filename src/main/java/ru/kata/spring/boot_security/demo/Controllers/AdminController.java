package ru.kata.spring.boot_security.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.Models.User;
import ru.kata.spring.boot_security.demo.Services.RoleService;
import ru.kata.spring.boot_security.demo.Services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.getUserByUsername(user.getUsername()));
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin";
    }


    @PostMapping
    public String createNewUser(@ModelAttribute("user") User user,
                                @RequestParam(value = "nameRoles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));

        userService.addUser(user);
        return "redirect:/admin/";
    }


    @GetMapping("{id}/edit")
    public String editUserForm(@ModelAttribute("user") User user,
                               ModelMap model,
                               @PathVariable("id") long id,
                               @RequestParam(value = "editRoles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        model.addAttribute("roles", roleService.getSetOfRoles(roles));
        model.addAttribute("user", userService.getUserById(id));
        return "admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id,
                         @RequestParam(value = "editRoles", required = false) String[] roles) {

        if (roles == null) {
            user.setRoles(userService.getUserById(id).getRoles());
        } else {
            user.setRoles(roleService.getSetOfRoles(roles));
        }

        userService.updateUser(user);
        return "redirect:/admin/";
    }


    @GetMapping("/{id}/remove")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/user")
    public String userAdminInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("roles", userService.getUserByUsername(user.getUsername()).getRoles());
        model.addAttribute("user", userService.getUserByUsername(user.getUsername()));
        return "admin-user";
    }


}
