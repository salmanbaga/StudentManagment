package com.Project.Student.Controller;

import com.Project.Student.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
}
