package com.Project.Student.Controller;

import com.Project.Student.Entity.Course;
import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Service.CourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourceController {

    @Autowired
    private CourceService courceService;


    @PostMapping("/course/{id}")
    public ResponceModel addCoursetoStudent(@PathVariable int id, @RequestBody Course course){
        return courceService.addCoursetoStudent(id,course);
    }

    @GetMapping("/getallcourses")
    public ResponceModel getallcourse(){
        return courceService.getcourse();
    }

    @GetMapping("/getcoursebyid/{id}")
    public ResponceModel getcoursebyid(@PathVariable int id){
        return courceService.getcoursebyid(id);
    }
}
