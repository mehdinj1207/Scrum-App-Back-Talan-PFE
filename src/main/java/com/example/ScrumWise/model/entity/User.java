package com.example.ScrumWise.model.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user",nullable = false)
    private Long idUser;
    @Column(name="cin")
    private String cin;
    @Column(name="firstname")
    private String firstname;
    @Column(name="lastname")
    private String lastname;
    @Column(name="position")
    private String position;
    @Column(name="role")
    private String role;
    @Column(name="active")
    private boolean isActive;
    @Column(name="email")
    private String email;
    @Column(name="tel")
    private String tel;
    @Lob
    @Column(name="data")
    private byte[] data;

    @Column(name="status", columnDefinition = "BOOLEAN default false")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "idDepartment")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "idCountry")
    private Country country;


}
