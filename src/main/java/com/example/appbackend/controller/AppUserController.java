package com.example.appbackend.controller;

import com.example.appbackend.controller.dto.RoleToUserForm;
import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Role;
import com.example.appbackend.service.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers(){
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser>saveUser(@RequestBody AppUser appUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveUser(appUser));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveRole(role));
    }

    @GetMapping("/user/getUser/{username}")
    public ResponseEntity<AppUser> getUser(@PathVariable String username){
        return ResponseEntity.ok().body(appUserService.getUser(username));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        appUserService.addRoleToAppUser(form.getUsername(), form.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/getUser")
    public AppUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return appUserService.getUser(auth.getPrincipal().toString());
    }

    @GetMapping ("/user/roles")
    public List<Role> getCurrentUserRoles(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return appUserService.getRoles(auth.getPrincipal().toString());
    }

    @PostMapping("/user/addUser")
    public AppUser addNewUser(@RequestBody AppUser appUser){
        return appUserService.saveUser(appUser);
    }


}