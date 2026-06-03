package com.Project.Student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subject {
    @Id
    private int code;
    private String name;


    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private List<Studententity> studententities=new ArrayList<>();
}
