package com.Project.Student.dto;

import lombok.Data;

@Data
public class Subjectdto {
    private int code;
    private String name;

   public Subjectdto(int code,String name){
        this.code=code;
        this.name=name;
    }

}
