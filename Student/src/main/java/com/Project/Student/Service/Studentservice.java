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
import org.springframework.cache.annotation.CacheEvict;
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


    @CacheEvict(value = "students", allEntries = true)
    public ResponceModel postdata(Studentdto studentdto){
//    log.info("Method to be start executed {}", LocalDateTime.now().toString());
    List<Studententity> exist=studentrepo.findByEmail(studentdto.getEmail());
    if(exist.isEmpty()){
        Studententity entity=Studentdto.toEntity(studentdto);
        Studententity save =studentrepo.save(entity);
        Studentdto dto=Studentdto.toDto(save);
        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                 ApiMessage.STUDENT_CREATED,
                                 dto);
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

@Cacheable(value = "students",sync = true)
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

@CacheEvict(value = "students",allEntries = true)
public ResponceModel putdata(Studentdto studentdto,int id){
   Studententity st= studentrepo.findById(id).orElse(null);
   List<Studententity> exits=studentrepo.findByEmail(studentdto.getEmail());
   if(exits.isEmpty()){
    if(st!=null){
        if(studentdto.getName() != null && !studentdto.getName().trim().isEmpty()) st.setName(studentdto.getName());
       if(studentdto.getEmail() != null && !studentdto.getEmail().trim().isEmpty()) st.setEmail(studentdto.getEmail());
       if(studentdto.getAge() != null) st.setAge(studentdto.getAge());
       Studententity student=studentrepo.save(st);
       Studentdto dto=Studentdto.toDto(student);

        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                  ApiMessage.UPDATED,
                                 dto
                );
    }}
          throw new DublicateExceptionRecource(ApiMessage.STUDENT_ALREADY_EXITS);
//        return new ResponceModel(HttpStatus.CONFLICT,
//                                 HttpStatus.CONFLICT.value(),
//                                 ApiMessage.STUDENT_ALREADY_EXITS,
//                            null);
}

@CacheEvict(value = "students",allEntries = true)
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

@Cacheable(value = "findByEmail",key = "#email")
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
        List<Studentdto> dtoList = st.stream()
                .map(Studentdto::toDto)
                .toList();
         return new ResponceModel( HttpStatus.OK,
                                  HttpStatus.OK.value(),
                                  ApiMessage.STUDENT_FOUND,
                                  dtoList);
    }
}

@Cacheable(value = "studCount")
public ResponceModel studcount(){
    Long st=studentrepo.count();
    return new ResponceModel(HttpStatus.OK,
                            HttpStatus.OK.value(),
                            "Total Student is..",
                              st);
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
            studententity.getEmail(),
            studententity.getStatus()
    ));
}

public List<Studentdto> getStudentAboveAge(int age){
    List<Studententity> student=studentrepo.getStudentAboveAge(age);
    return student.stream().map(s -> new
            Studentdto(s.getId(),s.getName(),s.getAge(),s.getEmail(),s.getStatus())).toList();
}

public List<Studentdto> getStudentByName(String name){
    List<Studententity> stud=studentrepo.getStudentByName(name);

    return stud.stream().map(studententity -> new Studentdto(studententity.getId(),
                                                 studententity.getName(),
                                                 studententity.getAge(),
                                                  studententity.getEmail(),
                                                  studententity.getStatus())).toList();
}
}
