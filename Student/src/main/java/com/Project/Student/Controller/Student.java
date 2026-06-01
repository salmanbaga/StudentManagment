package com.Project.Student.Controller;

import com.Project.Student.Models.ResponceModel;
import com.Project.Student.Service.Studentservice;
import com.Project.Student.dto.Studentdto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/student")
public class Student {
    @Autowired
    Studentservice studentservice;

    @PostMapping("/post")
    public ResponceModel postdata(@Valid @RequestBody Studentdto studentdto){
//        log.info("Insert the data");
//        System.out.println("Enter the process");           //login concept
      return studentservice.postdata(studentdto);
    }

    @GetMapping("/get")
    public ResponceModel getdata(){
        return studentservice.getdata();
    }

    @PutMapping("/put/{id}")
    public ResponceModel putdata(@PathVariable int id,@RequestBody Studentdto studentdto){
        return studentservice.putdata(studentdto,id);
    }

    @DeleteMapping("/delete/{id}")
    public String deletdata(@PathVariable int id){
        return studentservice.deletedata(id);
    }

    @GetMapping("/email/{email}")
    public ResponceModel findbyemail(@PathVariable String email){
        return studentservice.findbyemail(email);
    }

    @GetMapping("/count")
    public ResponceModel studcount(){
        return studentservice.studcount();
    }


    @GetMapping("/byPagination")
    public  List<Studentdto> usedByPagination(@RequestParam int pageno,
                                              @RequestParam int pagesize){
        return studentservice.usedByPagination(pagesize,pageno);
    }

    @GetMapping("/byPagable")
    public Page<Studentdto> usedByPagable(@RequestParam int pageno,
                                          @RequestParam int pagesize,
                                          @RequestParam String sortBy,
                                          @RequestParam String sortDirection) {
        return studentservice.usedByPageble(pagesize, pageno - 1, sortBy, sortDirection);
    }

    @GetMapping("/getStudentByJpql")
   public List<Studentdto> getStudentAboveAge(@RequestParam int age){
       return studentservice.getStudentAboveAge(age);
        }

   @GetMapping("/getStudentByName")
   public List<Studentdto>  getStudentByName(@RequestParam String name){
        return studentservice.getStudentByName(name);
   }
}

