package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImp;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import java.security.Principal;


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

    @GetMapping(value = "")
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByEmail(principal.getName()));
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roles", roleServiceImp.allRoles());
        model.addAttribute("emptyUser", new User());
        return "/admin";
    }

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute("emptyUser") User user,
                             @RequestParam(value = "checkedRoles") String[] selectResult) {
        for (String s : selectResult) {
            user.addRole(roleServiceImp.getRoleByName("ROLE_" + s));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@ModelAttribute("emptyUser") User user, @PathVariable("id") int id,
                             @RequestParam(value = "userRolesSelector") String[] selectResult) throws Exception {
        for (String s : selectResult) {
            user.addRole(roleServiceImp.getRoleByName("ROLE_" + s));
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

}
