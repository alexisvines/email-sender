package com.procanchas.email.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "email_template")
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = -5000147640861049353L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_email_template")
    private Long idEmailTemplate;

    @Column(name = "html")
    private String html;

    @Column(name = "subject")
    private String subject;

    @OneToMany(mappedBy = "emailTemplate",cascade = CascadeType.ALL )
    private Set<EmailTemplateLabel> emailTemplateLabels = new HashSet<>();


}
