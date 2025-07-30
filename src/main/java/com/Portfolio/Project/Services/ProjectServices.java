package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Repository.ProjectRepository;
import com.Portfolio.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServices implements IProjectServices{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }
    @Override
    public Project saveProject(Project project, String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + userName);
        }
        User user = optionalUser.get();
        project.setUser(user);
        Project savedProject = projectRepository.save(project);
        user.getProjects().add(savedProject);
        userRepository.save(user);

        return savedProject;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Project not found with id " + id));
    }

    @Override
    public List<Project> findAllByName(String name) {
        List<Project> projects = projectRepository.findAllByName(name);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found with name: " + name);
        }
        return projects;
    }
    public void deleteById(Long id){
        projectRepository.findById(id).ifPresentOrElse(projectRepository::delete, ()-> {
            throw new RuntimeException("Project not found");
        });
    }
    @Override
    public Project updateProject(Long id, Project project){
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Project not Found"));
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        projectRepository.save(existingProject);
        return existingProject;
    }
}
