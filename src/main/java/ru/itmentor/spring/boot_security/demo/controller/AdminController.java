package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImp;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleServiceImp roleServiceImp;

    @Autowired
    public AdminController(UserService userService, RoleServiceImp roleServiceImp) {
        this.userService = userService;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping()
    public String userListForAdmin(Model model) {
        model.addAttribute("Users", userService.allUsers());
        return "admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("roles", roleServiceImp.allRoles());
        model.addAttribute("user", userService.findUserById(id));
        return "editUserForAdmin";
    }

    @PostMapping(value = "/edit/{id}")
    public String edit(@ModelAttribute("user") User user,
                       @RequestParam("role") String role) {
        Set<Role> roles = new HashSet<>();

        if (role != null && role.equals("ROLE_ADMIN")) {
            roles.add(roleServiceImp.getRoleByName("ROLE_ADMIN"));
        } else {
            roles.add(roleServiceImp.getRoleByName("ROLE_USER"));
        }

        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
