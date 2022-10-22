package com.example.appbackend.service;

import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToAppUser(String username,String roleName);
    AppUser getUser(String username);
    List<Role> getRoles(String username);
    List<AppUser> getUsers();
}
