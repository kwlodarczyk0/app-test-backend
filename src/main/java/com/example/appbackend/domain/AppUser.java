package com.example.appbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
   //@GeneratedValue(strategy = AUTO)
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String username;
    @JsonIgnore //hide password field in response
    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
