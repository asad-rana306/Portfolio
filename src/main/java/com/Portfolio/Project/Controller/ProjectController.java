package com.Portfolio.Project.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/project")
@AllArgsConstructor
public class ProjectController {

    @GetMapping
    public String getAllProject(){
        return "Project is not Available";
    }
}
