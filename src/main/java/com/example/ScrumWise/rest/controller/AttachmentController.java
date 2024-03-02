package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.*;
import com.example.ScrumWise.repository.ProjectRepository;
import com.example.ScrumWise.rest.dto.RessourceRoleDto;
import com.example.ScrumWise.rest.dto.SprintDto;
import com.example.ScrumWise.service.AttachmentService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@CrossOrigin
public class AttachmentController {


    // Constructor dependency injection
    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService=attachmentService;
    }
    @Autowired
    private ProjectRepository projectRepository;


    //Uploading a file
    @PostMapping("/upload/project/{idProject}/{owner}")
    @RolesAllowed({"Manager","Consultant"})
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file,
                                   @PathVariable Long idProject, @PathVariable String owner ) throws Exception {

        Attachment attachment = new Attachment();

        String downloadURL = "";

        attachment = attachmentService.saveAttachment(file,idProject,owner);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("download/")
                .path(attachment.getId())
                .toUriString();

        return new ResponseData(attachment.getFileName(),
                downloadURL,
                file.getContentType(),
                file.getSize());

    }

    @GetMapping("download/{fileId}")
    @RolesAllowed({"Manager","Consultant"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+attachment.getFileName()
                + "\"")
                .body(new ByteArrayResource(attachment.getData()));

    }

    @GetMapping("file/{fileId}")
    @RolesAllowed({"Manager","Consultant"})
    public ResponseEntity<Attachment> getFileById(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok().body(attachment);

    }





    @GetMapping("/files")
    @RolesAllowed({"Manager","Consultant"})
    public Object allAttachments() {
        List<Attachment> attachments = attachmentService.getAllAttachment();
        return ResponseEntity.status(HttpStatus.OK).body(attachments);
    }

    //Get All attachments by Project
    @GetMapping("/files/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object attachmentsByProjectList(@PathVariable Long idProject) {

        List<Attachment> attachments = attachmentService.getattachmentsByProject(idProject);
        return ResponseEntity.status(HttpStatus.OK).body(attachments);

    }


    @DeleteMapping("deleteFile/{fileId}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object deleteFile(@PathVariable String fileId){

        attachmentService.deleteFile(fileId);
        return ResponseEntity.ok("File Deleted Successfully");
    }
}
