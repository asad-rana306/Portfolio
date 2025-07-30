package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.Project;

import java.util.List;

public interface IProjectServices {
    List<Project> getAllProject();
    Project saveProject(Project project, String userName);
    Project getProjectById(Long id);
    List<Project> findAllByName(String name);
    void deleteById(Long id);
    Project updateProject(Long id, Project project);
}
