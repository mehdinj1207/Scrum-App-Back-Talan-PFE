package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Epic;
import com.example.ScrumWise.model.entity.Project;

import java.util.List;

public interface EpicService {
    Epic getEpicById(Long id);
    List<Epic> getEpicList();
    Epic addEpic(Epic epic);
    Epic updateEpic(Long id, Epic epic);
    void deleteEpic(Long id);
    List<Epic> getEpicByProject(Project project);
}