package com.example.appbackend.repository;

import com.example.appbackend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByName(String name);

    List<Project> findProjectsByUsersUsername(String username);

    @Query(value = "SELECT app_user.username FROM app_user INNER JOIN project_users pu on app_user.id = pu.users_id INNER JOIN project p on p.id = pu.project_id WHERE p.name = ?1",nativeQuery = true)
    List<String> getProjectUsers(String projectName);

    @Query(value = "SELECT app_user.username FROM app_user WHERE app_user.username NOT IN (SELECT app_user.username FROM app_user INNER JOIN project_users pu on app_user.id = pu.users_id INNER JOIN project p on p.id = pu.project_id WHERE p.name=?1)",nativeQuery = true)
    List<String> getUsersAreNotInProject(String projectName);

    @Query(value = "SELECT productManager FROM Project WHERE name = ?1")
    String findProductManager(String projectName);


}

