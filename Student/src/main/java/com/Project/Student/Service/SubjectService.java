package com.Project.Student.Service;

import com.Project.Student.Entity.Studententity;
import com.Project.Student.Entity.Subject;
import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Repo.Studentrepo;
import com.Project.Student.Repo.SubjectRepo;
import com.Project.Student.dto.Studentdto;
import com.Project.Student.dto.Subjectdto;
import com.Project.Student.exception.DublicateExceptionRecource;
import com.Project.Student.exception.NotFoundException;
import com.Project.Student.util.ApiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepo subjectRepo;

    @Autowired
    Studentrepo studentrepo;

    @CacheEvict(value = "subject",key = "#subjectdto.code")
    public ResponceModel addSubject(Subjectdto subjectdto){
        Subject sc=Subjectdto.toEntity(subjectdto);
        Subject subject= subjectRepo.save(sc);
        Subjectdto dto=Subjectdto.toDto(subject);

        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                 "Subject Created Successfully",
                                  dto);
    }

    @CacheEvict(value = "subject",key = "#studid + '-' + #subid")
    public ResponceModel postSubjecttoStudent(int studid,int subid){
        Studententity sc=studentrepo.findById(studid).orElse(null);
        Subject sb=subjectRepo.findById(subid).orElse(null);

        if(sc==null){
            throw new NotFoundException(ApiMessage.STUDENT_NOT_FOUND);
        }
        if(sb == null){
            throw new NotFoundException(ApiMessage.SUBJECT_NOT_FOUND);
        }

        sc.getSubjects().add(sb);
        Studententity studententity=studentrepo.save(sc);
        Studentdto dto=Studentdto.toDto(studententity);

            return new ResponceModel(HttpStatus.CREATED,
                                     HttpStatus.CREATED.value(),
                                      "Subject Created WIth Student_Id",
                                        dto);

    }

    @Cacheable(value = "subject",key = "#studid")
    public ResponceModel searchSubjecttoStudent(int studid) {
        List<Subject> lt = subjectRepo.findByStudententities_Id(studid);
        if (lt.isEmpty())
        {
            throw new NotFoundException(ApiMessage.STUDENT_NOT_FOUND);
        }
        List<Subjectdto> dtoList = lt.stream()
                .map(Subjectdto::toDto)
                .toList();
        return new ResponceModel(HttpStatus.FOUND,
                                HttpStatus.FOUND.value(),
                                 "Subjects of Search Student_Id",
                                  dtoList);
    }
}
