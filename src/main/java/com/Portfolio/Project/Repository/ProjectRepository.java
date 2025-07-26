package com.Portfolio.Project.Repository;

import com.Portfolio.Project.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
