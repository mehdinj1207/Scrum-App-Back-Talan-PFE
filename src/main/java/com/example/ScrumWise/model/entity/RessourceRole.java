package com.example.ScrumWise.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ressource_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RessourceRole implements Serializable{
        /**
         * generated serial ID
         */
        private static final long serialVersionUID = 1L;

        /**
         * RessourceRole id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="idRessourceRole", updatable = false, nullable = false)
        private Long idRessourceRole;

        @Column(name="role")
        private String role;

}
