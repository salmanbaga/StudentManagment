package com.Project.Student.dto;



import com.Project.Student.Entity.Studententity;
import jakarta.persistence.Column;
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


    public Studententity toEntity() {
        return Studententity.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .email(this.email)
                .build();
    }

    public static Studentdto toDto(Studententity entity) {
        return new Studentdto(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getEmail()

        );
    }

}