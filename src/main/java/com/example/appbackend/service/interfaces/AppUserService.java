package com.example.appbackend.service.interfaces;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToAppUser(String username,String roleName);
    AppUser getUser(String username);
    List<Role> getRoles(String username);
    List<AppUser> getUsers();

}
