package com.example.ScrumWise.service.serviceImp;


import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.Participation;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.repository.ParticipationRepository;
import com.example.ScrumWise.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipationServiceImp implements ParticipationService {
    @Autowired
    ParticipationRepository participationRepository;

    @Override
    public void  addParticipation(Meet meet, User user) {
        Participation participation=new Participation();
        participation.setUser(user);
        participation.setMeet(meet);
        participationRepository.save(participation);
    }

    @Override
    public void deleteParticipation(Meet meet, User user) {
        Participation participation=participationRepository.findByUserAndMeet(user,meet);
    }

    @Override
    public List<User> getParticipants(Meet meet) {
        List<Participation> participations=participationRepository.findByMeet(meet);

        if(!participations.isEmpty()){
            List<User> participants=new ArrayList<User>();
            for(int i=0;i<participations.size();i++)
                participants.add(participations.get(i).getUser());
            return participants;
        }
        return null;
    }

    @Override
    public List<Meet> getMeetingByParticipant(User user) {
        List<Participation> participations=participationRepository.findByUser(user);
        if(!participations.isEmpty()){
            List<Meet> meets=new ArrayList<Meet>();
            for(int i=0;i<participations.size();i++)
                meets.add(participations.get(i).getMeet());
            return meets;
        }
        return null;
    }
    @Override
    public void deleteParticipationByMeet(Meet meet){
        participationRepository.deleteByMeet(meet);
    }
    @Override
    public void deleteById(Long id) {
        participationRepository.deleteById(id);
    }
    public List<Project> getDistinctProjectsByUserId(User user){
        return participationRepository.findDistinctProjectsByUser(user);
    }
    @Override
    public void deleteParticipationsByProjectId(Long idProject){
        participationRepository.deleteParticipationsByProjectId(idProject);
    }
}
