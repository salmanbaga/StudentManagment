package com.Project.Student.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponceModel implements Serializable {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object data;
}
