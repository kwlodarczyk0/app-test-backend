package com.example.appbackend.api;

import com.example.appbackend.api.dto.RoleToUserForm;
import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Role;
import com.example.appbackend.service.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/role/addToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        appUserService.addRoleToAppUser(form.getUsername(), form.getUsername());
        return ResponseEntity.ok().build();
    }
}