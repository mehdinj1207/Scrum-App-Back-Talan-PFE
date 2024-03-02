package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.exception.ResourceNotFoundException;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.model.entity.RessourceRole;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.repository.RessourceRepository;
import com.example.ScrumWise.service.RessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RessourceServiceImp implements RessourceService {
    @Autowired
    RessourceRepository ressourceRepository;

    @Override
    public List<Ressource> getRessourceList() {
        return ressourceRepository.findAll();
    }

    @Override
    public Ressource getRessourceById(Long id) {
        try{
            return ressourceRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Ressource not found!");
            return new Ressource();
        }
    }

    @Override
    public Ressource addRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }
    @Override
    public List<Ressource> getRessourceByProject(Project project) {
        List<Ressource> resources = ressourceRepository.findRessourceByProject(project);
        if (resources.isEmpty()) {
            throw new ResourceNotFoundException("No resources found for project with id: " + project.getIdProject());
        }
        return resources;
    }
    @Override
    public Ressource getRessourceByUserAndProject(User user, Project project) {
        Ressource ressource = ressourceRepository.findRessourceByUserAndProject(user, project);
        if (ressource == null) {
            throw new ResourceNotFoundException("No resource found for user with id: " + user.getIdUser() + " and project with id: " + project.getIdProject());
        }
        return ressource;
    }
    @Override
    public Ressource updateRessource(Long id, Ressource ressource) {
        Ressource res = ressource;
        res.setIdRessource(id);
        return  ressourceRepository.save(res);
    }

    @Override
    public void deleteRessource(Long id) {
        ressourceRepository.deleteById(id);
    }
    @Override
    public void deleteRessourceByUserAndProject(User user, Project project){
        Ressource ressource = ressourceRepository.findRessourceByUserAndProject(user, project);
        ressourceRepository.delete(ressource);
    }
    @Override
    public void deleteRessourceByProject(Project project){
        List <Ressource> ressources = ressourceRepository.findRessourceByProject(project);
        for (int i = 0 ;i<ressources.size();i++){
            ressourceRepository.delete(ressources.get(i));
        }
    }

    @Override
    public void deleteRessourcesByUser(User user){
        List <Ressource> ressources = ressourceRepository.findRessourceByUser(user);
        for (int i = 0 ;i<ressources.size();i++){
            ressourceRepository.delete(ressources.get(i));
        }
    }

    @Override
    public List<Ressource> getRessourceByUser(User user) {
        List<Ressource> resources = ressourceRepository.findRessourceByUser(user);
        if (resources.isEmpty()) {
            throw new ResourceNotFoundException("No resources found for project with user " + user);
        }
        return resources;
    }
    @Override
    public List<Project> getProjectsByUserFromRessources(Long idUser){
        return ressourceRepository.findDistinctProjectsByUserId(idUser);
    }

}
