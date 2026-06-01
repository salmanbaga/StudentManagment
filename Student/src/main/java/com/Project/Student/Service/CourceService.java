package com.Project.Student.Service;

import com.Project.Student.Entity.Course;
import com.Project.Student.Entity.Studententity;
import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Repo.CourseRepo;
import com.Project.Student.Repo.Studentrepo;
import com.Project.Student.dto.Coursedto;
import com.Project.Student.exception.DublicateExceptionRecource;
import com.Project.Student.exception.NotFoundException;
import com.Project.Student.util.ApiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CourceService {

    @Autowired
    private  CourseRepo courseRepo;
    @Autowired
    private Studentrepo studentrepo;


    @CacheEvict(value = "courses",allEntries = true)
    public ResponceModel addCoursetoStudent(int id, Course course){
        Studententity st=studentrepo.findById(id).orElse(null);

        if(st == null){
            throw new NotFoundException(ApiMessage.STUDENT_NOT_FOUND);
        }
        boolean courseAlreadyExists=courseRepo.existsByStudententityIdAndName(st.getId(),course.getName());
        if(courseAlreadyExists){
            throw new DublicateExceptionRecource(ApiMessage.COURSE_ALREADY_EXISTS);
        }
        course.setStudententity(st);
        Course cs= courseRepo.save(course);
        return new ResponceModel(HttpStatus.CREATED,
                                 HttpStatus.CREATED.value(),
                                 ApiMessage.COURSE_CREATED,
                                cs);
    }

    @Cacheable(value = "courses")
    public ResponceModel getcourse(){
        List<Course> course=courseRepo.findAll();
        List<Coursedto> dtoList=course
                .stream()
                .map(Coursedto::toDto)
                .toList();
        return new ResponceModel(HttpStatus.OK,
                                 HttpStatus.OK.value(),
                                 "GetAll Courses Successfully",
                                   dtoList);
    }

    @Cacheable(value = "CoursesById",key = "#id")
    public ResponceModel getcoursebyid(int id){
        List<Course> cs=courseRepo.findByStudententity_Id(id);
        List<Coursedto> dtoList=cs
                .stream()
                .map(Coursedto::toDto)
                .toList();

        if(cs.isEmpty()){
            throw new NotFoundException(ApiMessage.STUDENT_NOT_FOUND);
        }
        else {
            return new ResponceModel(HttpStatus.FOUND,
                                     HttpStatus.FOUND.value(),
                                      ApiMessage.COURSE_FOUND,
                                      dtoList);
        }
    }
}
