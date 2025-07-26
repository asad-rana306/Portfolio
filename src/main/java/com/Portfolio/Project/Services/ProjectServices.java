package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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
    public Project getProjecById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Project not found with id " + id));
    }
}
