package com.Project.Student.Repo;

import com.Project.Student.Entity.Studententity;
import com.Project.Student.dto.Studentdto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Studentrepo extends JpaRepository<Studententity,Integer> {


   List<Studententity> findByEmail(String email);

   @Query
           (value = "Select id,name,age from Student1 limit ?1 offset ?2",nativeQuery = true)
   List<Studentdto> usedByPagination(int pagesize, int pageno);


@Query
        ("Select s from Studententity s where s.age>:age")
List<Studententity> getStudentAboveAge(int age);

@Query
        ("Select s from Studententity s where s.name like %:name% and s.status=ACTIVE")
   List<Studententity> getStudentByName(String name);

@Query
        ("Select s from Studententity s where s.status=ACTIVE")
   List<Studententity> findByStatusActive();

}
