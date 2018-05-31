package com.procanchas.email.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  Reposotorio que contendrá las claves que iran dentro de un html
 *  para realizar el binding
 */
@Data
@Entity
public class TemplateAttribute implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String label;

}
