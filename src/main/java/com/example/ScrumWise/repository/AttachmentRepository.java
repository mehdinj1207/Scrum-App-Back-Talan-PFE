package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Attachment;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,String> {
    List<Attachment> findAttachmentByProject(Project project);
}
