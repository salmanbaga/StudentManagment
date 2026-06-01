package com.Project.Student.Repo;

import com.Project.Student.Entity.Course;
import com.Project.Student.dto.Coursedto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface CourseRepo extends JpaRepository<Course,String> {

   List<Course> findByStudententity_Id(int id);

   boolean existsByStudententityIdAndName(int studentId, String courseName);


}
