package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;
import java.util.List;
import java.util.Optional;


public interface RoleService {

    Role getRoleByName(String name);

    Optional<Role> getRoleById(int id);

    List<Role> allRoles();

}
