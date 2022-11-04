package com.example.appbackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @SequenceGenerator(
            name="project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Long id;
    private String name;

    /*@ManyToOne(fetch = LAZY)
    @JoinColumn(name = "moderator_id")
    private AppUser moderator;*/

    private String productManager;

    @ManyToMany(fetch = EAGER)
    private Collection<AppUser> users = new ArrayList<>();

    @ManyToMany(fetch = EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Task> tasks = new ArrayList<>();


}
