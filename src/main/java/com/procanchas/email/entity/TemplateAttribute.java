package com.procanchas.email.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

/**
 *  Reposotorio que contendra las claves que iran dentro de un html
 *  para realizar el binding
 */
@Data
@Entity
@Repository
public class TemplateAttribute {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String label;

}
