package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource,Long> {
 Ressource findRessourceByUserAndProject(User user, Project project);
 List<Ressource> findRessourceByProject(Project project);
 @Transactional
 @Modifying
 @Query("DELETE FROM Ressource r WHERE r.user.idUser = :userId")
 void deleteByUserId(@Param("userId") Long userId);
 List<Ressource> findRessourceByUser(User user);
 @Query("SELECT DISTINCT r.project FROM Ressource r WHERE r.user.idUser = :id1")
 List<Project> findDistinctProjectsByUserId(@Param("id1") Long idUser);



}
