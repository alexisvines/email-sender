package com.procanchas.email.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;


}
