package com.Project.Student.dto;

import com.Project.Student.Entity.Course;
import com.Project.Student.Entity.Enrollment;
import com.Project.Student.Entity.Studententity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EnrollmentDto {
    private int enrollid;
    private LocalDate enrollDate;

    @NotNull (message = "Student id is not Null")
    private int studentId;

    @NotNull (message = "Course id is not Null")
    private String courseId;
    
    public static Enrollment toEntity(EnrollmentDto enrollmentDto,
                               Studententity studententity,
                               Course course) {
        return Enrollment.builder()
                .enrollid(enrollmentDto.getEnrollid())
                .enrollDate(enrollmentDto.getEnrollDate())
                .student(studententity)
                .course(course)
                .build();
    }

    public static EnrollmentDto toDto(Enrollment enrollment) {
        return EnrollmentDto.builder()
                .enrollid(enrollment.getEnrollid())
                .enrollDate(enrollment.getEnrollDate())
                .studentId(enrollment.getStudent().getId())
                .courseId(enrollment.getCourse().getCourseId())
                .build();
    }


}

