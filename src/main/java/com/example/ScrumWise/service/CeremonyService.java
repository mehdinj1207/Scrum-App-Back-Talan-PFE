package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Ceremony;

import java.util.List;

public interface CeremonyService {
    Ceremony getCeremonyById(Long id);
    List<Ceremony> getCeremonies();

}

