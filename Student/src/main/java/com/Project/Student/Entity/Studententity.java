package com.Project.Student.Entity;

import com.Project.Student.Enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Student1")
public class Studententity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private int age;

    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;

    @OneToMany(mappedBy = "studententity")
    private List<Course> courses;

    @ManyToMany
    @JoinTable
            (name = "Students_Subjects",
             joinColumns = @JoinColumn(name = "Student_id"),
             inverseJoinColumns = @JoinColumn(name = "Subject_code"))
    @JsonIgnore
    private List<Subject> subjects=new ArrayList<>();



}
