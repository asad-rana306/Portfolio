package com.Portfolio.Project.Controller;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Repository.UserRepository;
import com.Portfolio.Project.Response.ApiResponse;
import com.Portfolio.Project.Services.IProjectServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/project")
@AllArgsConstructor
public class ProjectController {
    @Autowired
    private IProjectServices projectServices;
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProject(){
        try {
            List<Project> project = projectServices.getAllProject();
            return ResponseEntity.ok(new ApiResponse("nothing",project));
        } catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), "null"));
        }
    }
    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable Long id){
        try{
            Project project = projectServices.getProjectById(id);
            return ResponseEntity.ok(new ApiResponse("project fetched successfully",project));
        }catch (Exception e){
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("getAllByName/{name}")
    public ResponseEntity<ApiResponse> getAllProjectsByName(@PathVariable String name){
        try {
            List<Project> projects = projectServices.findAllByName(name);
            return ResponseEntity.ok(new ApiResponse("Projects fetched successfully", projects));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
