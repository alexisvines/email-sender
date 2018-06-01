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
@Table(name = "email_template_label")
public class EmailTemplateLabel implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_email_template_label")
    private Long idEmailTemplateLabel;

    @Column(name = "label")
    private String label;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_email_template")
    private EmailTemplate emailTemplate;

}
