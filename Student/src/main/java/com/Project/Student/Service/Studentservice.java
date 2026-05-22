package com.Project.Student.Service;

import com.Project.Student.Entity.Studententity;
import com.Project.Student.Enums.Status;
import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Repo.Studentrepo;
import com.Project.Student.Repo.SubjectRepo;
import com.Project.Student.dto.Studentdto;
import com.Project.Student.exception.DublicateExceptionRecource;
import com.Project.Student.exception.NotFoundException;
import com.Project.Student.util.ApiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class Studentservice {
    @Autowired
    Studentrepo studentrepo;
    @Autowired
    SubjectRepo subjectRepo;

public ResponceModel postdata(Studentdto studentdto){
//    log.info("Method to be start executed {}", LocalDateTime.now().toString());
    List<Studententity> exist=studentrepo.findByEmail(studentdto.getEmail());
    if(exist.isEmpty()){
        Studententity entity=studentdto.toEntity();
        Studententity save =studentrepo.save(entity);
        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                 ApiMessage.STUDENT_CREATED,
                                 save);
    }
     else {
         throw new DublicateExceptionRecource(ApiMessage.STUDENT_ALREADY_EXITS);
//         log.info("method to be end Successfully {}",LocalDateTime.now().toString());
//         return new ResponceModel(HttpStatus.CONFLICT,
//                                  HttpStatus.CONFLICT.value(),
//                                  ApiMessage.STUDENT_ALREADY_EXITS,
//                                  null);
    }
}

@Cacheable(value = "students")
public ResponceModel getdata(){
  List<Studententity> studententity= studentrepo.findByStatusActive();
    List<Studentdto> dtoList=studententity
            .stream()
            .map(Studentdto::toDto)
            .toList();
    System.out.println("Data Fetch From Database");
    return new ResponceModel(HttpStatus.OK,
                             HttpStatus.OK.value(),
                             "Get All Student",
                               dtoList);
}

public ResponceModel putdata(Studententity studententity,int id){
   Studententity st= studentrepo.findById(id).orElse(null);
   List<Studententity> exits=studentrepo.findByEmail(studententity.getEmail());
   if(exits.isEmpty()){
    if(st!=null){
        if(studententity.getName() != null) st.setName(studententity.getName());
       if(studententity.getEmail() != null) st.setEmail(studententity.getEmail());
       if(studententity.getAge() != 0) st.setAge(studententity.getAge());
       if(studententity.getStatus() != null) st.setStatus(studententity.getStatus());
       Studententity student=studentrepo.save(st);
        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                  ApiMessage.UPDATED,
                                  student
                );
    }}
          throw new DublicateExceptionRecource(ApiMessage.STUDENT_ALREADY_EXITS);
//        return new ResponceModel(HttpStatus.CONFLICT,
//                                 HttpStatus.CONFLICT.value(),
//                                 ApiMessage.STUDENT_ALREADY_EXITS,
//                            null);
}

public String deletedata(int id){
    Studententity st= studentrepo.findById(id).orElse(null);
    if(st != null && st.getStatus() == Status.INACTIVE){
        return "Student already inactive";
    }
    else if(st != null){
       st.setStatus(Status.INACTIVE);
       studentrepo.save(st);
        return "Data deleted successfully";
    }
    return "INVALID ID...";
}

public ResponceModel findbyemail(String email){
    List<Studententity> st=studentrepo.findByEmail(email);
    if(st.isEmpty()){
        throw new NotFoundException(ApiMessage.STUDENT_NOT_FOUND);
//        return new ResponceModel(HttpStatus.NOT_FOUND,
//                                 HttpStatus.NOT_FOUND.value(),
//                                 ApiMessage.STUDENT_FOUND,
//                                  null);
    }
    else {
        return new ResponceModel( HttpStatus.FOUND,
                                  HttpStatus.FOUND.value(),
                                  ApiMessage.STUDENT_FOUND,
                                  st);
    }
}

public String studcount(){
    Long st=studentrepo.count();
    return "Total number of Students==>"+st;
}


public List<Studentdto> usedByPagination(int pagesize, int pageno){
    int offset=(pageno -1) * pagesize;
    List<Studentdto> stude= studentrepo.usedByPagination(pagesize,offset);
    return stude;
}

public Page<Studentdto> usedByPageble(int pagesize,int pageno,String sortBy,String sortDirection){

   Sort sort= sortDirection.equalsIgnoreCase("ASC")? Sort.by(sortBy).ascending():
           Sort.by(sortBy).descending();

    Pageable pageable= PageRequest.of(pageno,pagesize,sort);
    Page<Studententity> stud=studentrepo.findAll(pageable);

    return stud.map(studententity -> new Studentdto(
            studententity.getId(),
            studententity.getName(),
            studententity.getAge(),
            studententity.getEmail()
    ));
}

public List<Studentdto> getStudentAboveAge(int age){
    List<Studententity> student=studentrepo.getStudentAboveAge(age);
    return student.stream().map(s -> new
            Studentdto(s.getId(),s.getName(),s.getAge(),s.getEmail())).toList();
}

public List<Studentdto> getStudentByName(String name){
    List<Studententity> stud=studentrepo.getStudentByName(name);

    return stud.stream().map(studententity -> new Studentdto(studententity.getId(),
                                                 studententity.getName(),
                                                 studententity.getAge(),
                                                  studententity.getEmail())).toList();
}
}
