package com.Project.Student.dto;

import com.Project.Student.Entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coursedto {
    private String courseId;
    private String name;


    public static Coursedto toDto(Course course){
        return Coursedto.builder()
                .courseId(String.valueOf(course.getCourseId()))
                .name(course.getName())
                .build();
    }

    public static Course toEntity(Coursedto coursedto){
        return Course.builder()
                .courseId(String.valueOf(coursedto.getCourseId()))
                .name(coursedto.getName())
                .build();
    }

}
