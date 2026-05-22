package com.Project.Student.Repo;

import com.Project.Student.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Integer> {

    boolean existsByCourse_courseIdAndStudent_Id(String courseId, int studentId);
}
