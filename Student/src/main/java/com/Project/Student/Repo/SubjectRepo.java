package com.Project.Student.Repo;

import com.Project.Student.Entity.Subject;
import com.Project.Student.dto.Subjectdto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject,Integer> {

List<Subject> findByStudententities_Id(int id);
}
