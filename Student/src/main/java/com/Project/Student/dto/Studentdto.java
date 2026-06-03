package com.Project.Student.dto;



import com.Project.Student.Entity.Studententity;
import com.Project.Student.Enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studentdto implements Serializable {

    private int id;

    @NotBlank(message = "Name can not be empty")
    private String name;

    @Min(value = 1 ,message = "Age must be 0 to above")
    @NotNull(message = "Age Can Not be Null.....")
    private Integer age;

    @Email(message = "Email Should be Valid")
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;


    public static Studententity toEntity(Studentdto dto) {
        return Studententity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .email(dto.getEmail())
                .status(dto.getStatus())
                .build();
    }

    public static Studentdto toDto(Studententity entity) {
        return  Studentdto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .build();
    }


}