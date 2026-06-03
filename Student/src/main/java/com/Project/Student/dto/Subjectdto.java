package com.Project.Student.dto;

import com.Project.Student.Entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subjectdto {
    private int code;
    private String name;

   public static Subject toEntity(Subjectdto subjectdto){
       return Subject.builder()
               .code(subjectdto.getCode())
               .name(subjectdto.getName())
               .build();
   }

    public static Subjectdto toDto(Subject subject){
        return Subjectdto.builder()
                .code(subject.getCode())
                .name(subject.getName())
                .build();
    }
}
