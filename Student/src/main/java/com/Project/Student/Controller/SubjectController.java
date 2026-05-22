package com.Project.Student.Controller;

import com.Project.Student.Entity.Subject;
import com.Project.Student.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("/addsubject")
    public String addSubject(@RequestBody Subject subject){
        return subjectService.addSubject(subject);
    }

    @PostMapping("/post/{studid}/{subid}")
    public String postsubject(@PathVariable int studid, @PathVariable int subid){
        return subjectService.postSubjecttoStudent(studid,subid);
    }

    @GetMapping("/searchSubjecttoStudent/{studid}")
    public String searchSubjects(@PathVariable int studid){
        return subjectService.searchSubjecttoStudent(studid);
    }
}
