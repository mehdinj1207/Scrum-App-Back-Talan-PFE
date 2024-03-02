package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface RessourceService {
    public List<Ressource> getRessourceList();
    public Ressource getRessourceById(Long id);
    public List<Ressource> getRessourceByProject(Project project);
    public Ressource getRessourceByUserAndProject(User user, Project project);
    public Ressource addRessource(Ressource ressource);
    public Ressource updateRessource(Long id,Ressource ressource);
    public void deleteRessource(Long id);
    public void deleteRessourceByUserAndProject(User user, Project project);

    public void deleteRessourceByProject(Project project);
    public void deleteRessourcesByUser(User user);
    public List<Ressource> getRessourceByUser(User user);
    List<Project> getProjectsByUserFromRessources(Long idUser);

}
