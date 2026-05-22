package com.Project.Student.Service;

import com.Project.Student.Entity.Studententity;
import com.Project.Student.Entity.Subject;
import com.Project.Student.Repo.Studentrepo;
import com.Project.Student.Repo.SubjectRepo;
import com.Project.Student.dto.Subjectdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepo subjectRepo;

    @Autowired
    Studentrepo studentrepo;

    public String addSubject(Subject subject){
        subjectRepo.save(subject);
        return "Subject Added Successfully";
    }

    public String postSubjecttoStudent(int studid,int subid){
        Studententity sc=studentrepo.findById(studid).orElse(null);
        Subject sb=subjectRepo.findById(subid).orElse(null);

        if(sc==null || sb==null){
            return "Student_Id or Subject_code Not Found";
        }
        else {
            sc.getSubjects().add(sb);
            studentrepo.save(sc);

            return "Data Successfully Added";
        }
    }

    public String searchSubjecttoStudent(int studid) {
        Studententity sc = studentrepo.findById(studid).orElse(null);

        if (sc == null) {
            return "Student_Id not Found";
        }

        List<Subjectdto> lt = subjectRepo.findByStudententities_Id(studid);
        if (lt.isEmpty())
        {
            return "Student_Id " + studid + " not have a Subjects";
        }
        return "List Of Subjects Student_Id" + " " + studid + subjectRepo.findByStudententities_Id(studid);
    }
}
