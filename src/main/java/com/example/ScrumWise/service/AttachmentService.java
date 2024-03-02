package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file, Long idProject, String owner) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;

    List<Attachment> getAllAttachment();

    void deleteFile(String fileId);

    List<Attachment> getattachmentsByProject(Long idProject);
}
