package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.Project;

import java.util.List;

public interface IProjectServices {
    List<Project> getAllProject();
    Project saveProject(Project project);
    Project getProjecById(Long id);
}
