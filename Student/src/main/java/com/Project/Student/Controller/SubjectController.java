package com.Project.Student.Controller;

import com.Project.Student.Entity.Subject;
import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Service.SubjectService;
import com.Project.Student.dto.Subjectdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("/addsubject")
    public ResponceModel addSubject(@RequestBody Subjectdto subjectdto){
        return subjectService.addSubject(subjectdto);
    }

    @PostMapping("/post/{studid}/{subid}")
    public ResponceModel postsubject(@PathVariable int studid, @PathVariable int subid){
        return subjectService.postSubjecttoStudent(studid,subid);
    }

    @GetMapping("/searchSubjecttoStudent/{studid}")
    public ResponceModel searchSubjects(@PathVariable int studid){
        return subjectService.searchSubjecttoStudent(studid);
    }
}
