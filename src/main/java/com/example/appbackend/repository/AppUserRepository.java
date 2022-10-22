package com.example.appbackend.repository;

import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);

    @Query("SELECT roles FROM AppUser WHERE username=?1")
    List<Role> getUserRoles(String username);

}
