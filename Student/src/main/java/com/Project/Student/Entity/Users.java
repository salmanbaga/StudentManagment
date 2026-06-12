package com.Project.Student.Entity;

import com.Project.Student.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Users {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
