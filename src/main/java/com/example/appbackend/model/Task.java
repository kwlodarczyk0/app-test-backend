package com.example.appbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(
            name="task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String code;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    private Date creationDate;
    private String description;

}
