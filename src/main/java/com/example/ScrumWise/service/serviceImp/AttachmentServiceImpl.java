package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Attachment;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.repository.AttachmentRepository;
import com.example.ScrumWise.repository.ProjectRepository;
import com.example.ScrumWise.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class AttachmentServiceImpl implements AttachmentService {


    @Autowired
    private ProjectRepository projectRepository;

    //Constructor Dependency Injection
    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment saveAttachment(MultipartFile file, Long idProject,String owner) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try{
                if(fileName.contains("..")){
                    throw new Exception("Filename contains invalid path sequence"+fileName);
                }
                Project project = projectRepository.findById(idProject).get();

                Attachment attachment = new Attachment(fileName,
                        file.getContentType(),
                        file.getSize(),
                        file.getBytes());
                attachment.setProject(project);
                attachment.setOwner(owner);
                return attachmentRepository.save(attachment);
            }
        catch (Exception e){
                throw new Exception("Could not save file : "+fileName+" fileType : "+file.getContentType());
            }

    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId)
                );
    }

    @Override
    public List<Attachment> getAllAttachment() {
        return attachmentRepository.findAll();
    }

    @Override
    public void deleteFile(String fileId) {
        attachmentRepository.deleteById(fileId);
    }

    @Override
    public List<Attachment> getattachmentsByProject(Long idProject) {
        Project project = projectRepository.findById(idProject).get();
        return attachmentRepository.findAttachmentByProject(project);
    }


}
