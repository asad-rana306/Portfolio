package com.Portfolio.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
//    public void setId(Long id){
//        this.id=id;
//    }
//    public Long getId(){
//        return id;
//    }
//    public void setName(String name){
//        this.name=name;
//    }
//    public void setDescription(String description){
//        this.description=description;
//    }
//    public String getName(){
//        return name;
//    }
//    public String getDescription(){
//        return description;
//    }
}
