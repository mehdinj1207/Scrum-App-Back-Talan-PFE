package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.RessourceRole;
import com.example.ScrumWise.repository.RessourceRoleRepository;
import com.example.ScrumWise.service.RessourceRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RessourceRoleServiceImp implements RessourceRoleService {
    @Autowired
    RessourceRoleRepository ressourceRoleRepository;
    @Override
    public List<RessourceRole> getRessourceRoleList() {
        return ressourceRoleRepository.findAll();
    }

    @Override
    public RessourceRole getRessourceRoleById(Long id) {
        try{
            return ressourceRoleRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("RessourceRole not found!");
            return new RessourceRole();
        }
    }

    @Override
    public RessourceRole addRessourceRole(RessourceRole role) {
        return ressourceRoleRepository.save(role);
    }

    @Override
    public RessourceRole updateRessourceRole(Long id, RessourceRole role) {
        RessourceRole r =role;
        r.setIdRessourceRole(id);
        return ressourceRoleRepository.save(r);
    }

    @Override
    public void deleteRessourceRole(Long id) {
        ressourceRoleRepository.deleteById(id);
    }
}
