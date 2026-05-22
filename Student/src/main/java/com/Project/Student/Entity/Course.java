package com.Project.Student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Course {

    @Id
    private String courseId;
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "Student_id")     //@Joincolumn used to many side Class
    private Studententity studententity;

    @PrePersist
    public void prePersist(){
       this.courseId= UUID.randomUUID().toString();
    }


    private int maxEnrollment;
    private int currentEnrollment;
}
