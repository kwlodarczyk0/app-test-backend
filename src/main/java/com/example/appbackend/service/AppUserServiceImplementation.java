package com.example.appbackend.service;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Role;
import com.example.appbackend.repository.AppUserRepository;
import com.example.appbackend.repository.RoleRepository;
import com.example.appbackend.service.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImplementation implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository; //RequiredArgsConstructor
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser user = appUserRepository.findByUsername(username);
        if(user==null){
            log.error("User not found in the db");
            throw new UsernameNotFoundException("User not found in the db");
        } else {
            log.info("User found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }


    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new {} to database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
        addRoleToAppUser(user.getUsername(),"ROLE_USER");
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new {} to the database",role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToAppUser(String username, String roleName) {
        log.info("Adding new role: {} to user: {}",roleName,username);
        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Get user {} from db",username);
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<Role> getRoles(String username) {
        return appUserRepository.getUserRoles(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("fetch new users");
        return appUserRepository.findAll();
    }

    @Override
    public AppUser changeUserPassword(String username, String newPassword,String repeatedNewPassword,String oldPassword) throws ApplicationContextException {
        AppUser user = appUserRepository.findByUsername(username);

        if(user==null) throw new UsernameNotFoundException("User not found in the db");

        if(!newPassword.equals(repeatedNewPassword)) throw new ApplicationContextException("Passwords are not the same");
        boolean oldPassword1 = passwordEncoder.matches(oldPassword,user.getPassword());
        if(!oldPassword1) throw new ApplicationContextException("Inncorect old password");
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }


}
