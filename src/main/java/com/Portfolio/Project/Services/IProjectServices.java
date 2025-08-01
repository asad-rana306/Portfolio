package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.Project;
import com.Portfolio.Project.Model.User;

import java.util.List;
import java.util.Optional;

public interface IProjectServices {
    List<Project> getAllProject();
    Project saveProject(Project project, User user);
    Project getProjectById(Long id);
    List<Project> findAllByName(String name);
    void deleteById(Long id);
    Project updateProject(Long id, Project project);
}
