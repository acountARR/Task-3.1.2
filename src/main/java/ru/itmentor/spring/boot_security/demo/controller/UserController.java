package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImp;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private RoleServiceImp roleServiceImp;

    @Autowired
    public void setUserService(UserService userService, RoleServiceImp roleServiceImp) {
        this.userService = userService;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping("/editUser/{id}")
    public String editUserForUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findUserById(id));
        return "editUserForUser";
    }

    @PostMapping(value = "/edit/{id}")
    public String edit(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleServiceImp.getRoleByName("ROLE_USER"));
        user.setRoles(roles);

        userService.updateUser(user);
        return "redirect:/user";
    }

     @GetMapping()
    public String show(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }


}
