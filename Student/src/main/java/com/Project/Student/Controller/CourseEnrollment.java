package com.Project.Student.Controller;


import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Service.EnrollmentService;
import com.Project.Student.dto.EnrollmentDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/enrollment")
public class CourseEnrollment {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/post")
    public ResponceModel insertEnrollment(@RequestBody @Valid  EnrollmentDto enrollmentDto){
        return enrollmentService.insertEnroll(enrollmentDto);
    }

    @GetMapping("/getall")
    public ResponceModel getAllEnrollment(@RequestParam int pageNo,@RequestParam int pageSize){
        return enrollmentService.getAllEnrollment(pageNo,pageSize);
    }

    @PutMapping("/updateEnrollment/{id}")
    public ResponceModel updateEnrollments(@PathVariable int id,
                                           @RequestBody EnrollmentDto enrollmentDto){
         return enrollmentService.updateEnrollment(id,enrollmentDto);
    }

    @DeleteMapping("/deleteEnrollment/{id}")
    public ResponceModel deleteEnrollment(@PathVariable int id){
        return enrollmentService.deletEnrollment(id);
    }
}
