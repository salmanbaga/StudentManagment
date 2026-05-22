package com.Project.Student.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Enrollment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int enrollid;

    private LocalDate enrollDate;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Studententity student;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @PrePersist
    public void prePersistEnroll(){
        this.enrollDate=enrollDate.now();
    }
}
