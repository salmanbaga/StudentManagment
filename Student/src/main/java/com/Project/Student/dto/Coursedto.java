package com.Project.Student.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Coursedto {
    private UUID courseId;
    private String name;

    Coursedto(UUID courseId,String name){
        this.courseId=courseId;
        this.name=name;
    }

}
