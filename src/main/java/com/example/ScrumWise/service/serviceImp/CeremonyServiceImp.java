package com.example.ScrumWise.service.serviceImp;


import com.example.ScrumWise.model.entity.Ceremony;
import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.repository.CeremonyRepository;
import com.example.ScrumWise.service.CeremonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CeremonyServiceImp implements CeremonyService {
    @Autowired
    CeremonyRepository ceremonyRepository;

    @Override
    public Ceremony getCeremonyById(Long id) {
        try{
            return ceremonyRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Ceremony not found!");
            return new Ceremony();
        }
    }

    @Override
    public List<Ceremony> getCeremonies() {
        return ceremonyRepository.findAll();

    }
}
