package com.Portfolio.Project.Controller;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Repository.UserRepository;
import com.Portfolio.Project.Response.ApiResponse;
import com.Portfolio.Project.Services.IProjectServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Security;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    private IProjectServices projectServices;
    @DeleteMapping("DeleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        try {
            projectServices.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("UpdateProjectById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable Long id, @RequestBody Project project){
        try {
            Project theProject = projectServices.updateProject(id, project);
            return ResponseEntity.ok(new ApiResponse("Project updated Successully",project));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> saveProjectWithUser(@RequestBody Project project){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
           Optional<User> user = userRepository.findByUserName(userName);
            User realUser = user.orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + userName));
            Project theproject = projectServices.saveProject(project, realUser);
            return ResponseEntity.ok(new ApiResponse("project saved successfully",theproject));
        }catch(Exception e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
