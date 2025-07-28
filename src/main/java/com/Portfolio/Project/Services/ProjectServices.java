package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServices implements IProjectServices{
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }
    @Override
    public Project saveProject(Project project) {
        projectRepository.save(project);
        return project;
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
