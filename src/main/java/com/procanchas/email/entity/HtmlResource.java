package com.procanchas.email.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
public class HtmlResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=40)
    private String html;

    @Column
    private String subject;

}
