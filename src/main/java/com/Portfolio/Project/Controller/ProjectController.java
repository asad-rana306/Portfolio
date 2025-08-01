package com.Portfolio.Project.Controller;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Repository.UserRepository;
import com.Portfolio.Project.Response.ApiResponse;
import com.Portfolio.Project.Services.IProjectServices;
import com.Portfolio.Project.Services.ProjectServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Optional<User> user1 = userRepository.findByUserName(userName);
            if (user1.isEmpty()) {
                throw new UsernameNotFoundException("User not found with username: " + userName);
            }
            User user = user1.get();
            List<Project> project = user.getProjects();
            return ResponseEntity.ok(new ApiResponse("nothing",project));
        } catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), "null"));
        }
    }
    @PostMapping
    public ResponseEntity<ApiResponse> saveProject(@RequestBody Project project){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Project theproject = projectServices.saveProject(project, userName);
            return ResponseEntity.ok(new ApiResponse("project saved successfully",theproject));
        }catch(Exception e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable Long id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Optional<User> user = userRepository.findByUserName(userName);
            if(user.isEmpty()){
                throw new UsernameNotFoundException("user not found with name: " + userName);
            }
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String user = authentication.getName();
            if(user.isEmpty())
                throw new UsernameNotFoundException("only login user can search: " + user);
            List<Project> projects = projectServices.findAllByName(name);
            return ResponseEntity.ok(new ApiResponse("Projects fetched successfully", projects));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("DeleteById/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        try {
            projectServices.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("UpdateProjectById/{id}")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable Long id, @RequestBody Project project){
        try {
            Project theProject = projectServices.updateProject(id, project);
            return ResponseEntity.ok(new ApiResponse("Project updated Successully",project));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}
