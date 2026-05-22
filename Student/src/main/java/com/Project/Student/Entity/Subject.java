package com.Project.Student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@Entity
public class Subject {
    @Id
    private int code;
    private String name;


    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private List<Studententity> studententities=new ArrayList<>();
}
