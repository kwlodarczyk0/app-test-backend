package com.example.appbackend.config;

import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Role;
import com.example.appbackend.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class StartConfiguration {
    @Bean
    CommandLineRunner run(AppUserService appUserService){
        return args -> {
            appUserService.saveRole(new Role(null,"ROLE_USER"));
            appUserService.saveRole(new Role(null,"ROLE_MANAGER"));
            appUserService.saveRole(new Role(null,"ROLE_ADMIN"));
            appUserService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

            appUserService.saveUser(new AppUser(null,"Hohn","test","1234",new ArrayList<>()));


            appUserService.addRoleToAppUser("test","ROLE_USER");
        };
    }
}
