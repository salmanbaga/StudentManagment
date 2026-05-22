package com.Project.Student.Service;

import com.Project.Student.Entity.Course;
import com.Project.Student.Entity.Enrollment;
import com.Project.Student.Entity.Studententity;
import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Repo.CourseRepo;
import com.Project.Student.Repo.EnrollmentRepo;
import com.Project.Student.Repo.Studentrepo;
import com.Project.Student.dto.EnrollmentDto;
import com.Project.Student.exception.DublicateExceptionRecource;
import com.Project.Student.exception.MaxLimitexception;
import com.Project.Student.exception.NotFoundException;
import com.Project.Student.util.ApiMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private Studentrepo studentrepo;

    @Autowired
    private CourseRepo courseRepo;

  @Transactional(rollbackOn = Exception.class)
    public ResponceModel insertEnroll(EnrollmentDto enrollmentDto){
        Studententity st=studentrepo.findById(enrollmentDto.getStudentId()).orElse(null);
        if(st == null){
            throw new NotFoundException(ApiMessage.STUDENT_NOT_FOUND);
        }
        Course cs=courseRepo.findById(enrollmentDto.getCourseId ()).orElse(null);
        if(cs == null){
            throw new NotFoundException(ApiMessage.COURSE_NOT_FOUND);
        }
        else if (cs.getCurrentEnrollment() == cs.getMaxEnrollment()) {
            throw new MaxLimitexception(ApiMessage.MAX_LIMIT_REACHED);
        }
        boolean studentAlreadyEnrolled = enrollmentRepo.existsByCourse_courseIdAndStudent_Id(cs.getCourseId(),st.getId());
        if(studentAlreadyEnrolled){
            throw new DublicateExceptionRecource(ApiMessage.ENROLLMENT_ALREADY_EXITS);
        }
            Enrollment enrollment=EnrollmentDto.toEntity(enrollmentDto,st,cs);
            cs.setCurrentEnrollment(cs.getCurrentEnrollment() +1);

           Enrollment saved=enrollmentRepo.save(enrollment);

           EnrollmentDto enrollmentDto1=EnrollmentDto.toDto(saved);


        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                 "Enrollment Created",
                                 enrollmentDto1);
    }

    public ResponceModel getAllEnrollment(int pageNo,int pageSize){
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
        Page<Enrollment> enrollments=enrollmentRepo.findAll(pageable);
        List<EnrollmentDto> dtoList=enrollments.getContent()
                .stream()
                .map(EnrollmentDto::toDto)
                .toList();
        return new ResponceModel(HttpStatus.OK,
                                 HttpStatus.OK.value(),
                                 ApiMessage.ENROLLMENT_FETCH_SUCCESS,
                                 dtoList);
    }

    public ResponceModel updateEnrollment(int enrollId,EnrollmentDto enrollmentDto){
      Enrollment enrollment=enrollmentRepo.findById(enrollId).orElse(null);
      if(enrollment == null){
          throw new NotFoundException(ApiMessage.ENROLLMENT_NOT_FOUND);
      }
      else {
          Studententity st=studentrepo.findById(enrollmentDto.getStudentId()).orElse(null);
      if(st == null){
          throw new NotFoundException(
                                      ApiMessage.STUDENT_NOT_FOUND
                                      );
      }
          Course course=courseRepo.findById(enrollmentDto.getCourseId()).orElse(null);
          if(course == null){
              throw new NotFoundException(ApiMessage.COURSE_NOT_FOUND);
          }
          boolean studentAlreadyEnrolled=enrollmentRepo.existsByCourse_courseIdAndStudent_Id(course.getCourseId(),st.getId());
          if(studentAlreadyEnrolled){
              throw new DublicateExceptionRecource(ApiMessage.ENROLLMENT_ALREADY_EXITS);
          }

          enrollment.setStudent(st);
          enrollment.setCourse(course);
          Enrollment saved=enrollmentRepo.save(enrollment);
          EnrollmentDto dto=EnrollmentDto.toDto(saved);

          return new ResponceModel(HttpStatus.OK,
                                   HttpStatus.OK.value(),
                                  "Enrollment Updated Successfully",
                                   dto
                                   );
      }
      }

      public ResponceModel deletEnrollment(int enrollId){
      Enrollment enrollment=enrollmentRepo.findById(enrollId).orElse(null);
      if(enrollment == null){
          throw new NotFoundException(ApiMessage.ENROLLMENT_NOT_FOUND);
      }
      else {
          enrollmentRepo.deleteById(enrollId);
         EnrollmentDto dto=EnrollmentDto.toDto(enrollment);

         return new ResponceModel(HttpStatus.OK,
                                  HttpStatus.OK.value(),
                                  ApiMessage.ENROLLMENT_DELETED,
                                   dto);
      }
  }

}
