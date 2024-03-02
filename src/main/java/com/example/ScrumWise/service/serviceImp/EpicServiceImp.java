package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.exception.ResourceNotFoundException;
import com.example.ScrumWise.model.entity.Epic;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.repository.EpicRepository;
import com.example.ScrumWise.repository.ProjectRepository;
import com.example.ScrumWise.repository.TicketRepository;
import com.example.ScrumWise.service.EpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EpicServiceImp implements EpicService {
    @Autowired
    private EpicRepository epicRepository ;
    @Autowired
    private TicketRepository ticketRepository ;
    @Autowired
    private ProjectRepository projectRepository ;
    @Override
    public Epic getEpicById(Long id) {
        try{
            return epicRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Epic not found!");
            return new Epic();
        }
    }

    @Override
    public List<Epic> getEpicList() {
        return epicRepository.findAll();
    }

    @Override
    public Epic addEpic(Epic epic) {
        return epicRepository.save(epic);
    }

    @Override
    public Epic updateEpic(Long id, Epic epic) {
        getEpicById(id);
        epic.setIdEpic(id);
        return epicRepository.save(epic);
    }

    @Override
    public void deleteEpic(Long id) {
     List<Ticket> tickets =ticketRepository.findTicketByEpic(epicRepository.findById(id).get());
        for (Ticket ticket : tickets) {
            ticket.setEpic(null);
            ticketRepository.save(ticket);
        }
        epicRepository.deleteById(id);
    }
    @Override
    public List<Epic> getEpicByProject(Project project) {
        List<Epic> epics = epicRepository.findEpicByProject(project);
        if (epics.isEmpty()) {
            throw new ResourceNotFoundException("No epics found for project with id: " + project.getIdProject());
        }
        return epics;
    }
}

