package com.example.ScrumWise.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    private String fileName;
    private String fileType;
    private long fileSize;

    private String owner;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name="id_project")
    @JsonBackReference
    private Project project;

    public Attachment(String fileName, String fileType,long size, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = size;
        this.data = data;
    }


}
