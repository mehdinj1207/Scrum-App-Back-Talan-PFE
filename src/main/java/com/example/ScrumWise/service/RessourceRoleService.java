package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.model.entity.RessourceRole;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface RessourceRoleService {
    public List<RessourceRole> getRessourceRoleList();
    public RessourceRole getRessourceRoleById(Long id);
    public RessourceRole addRessourceRole(RessourceRole role);
    public RessourceRole updateRessourceRole(Long id,RessourceRole role);
    public void deleteRessourceRole(Long id);
}
