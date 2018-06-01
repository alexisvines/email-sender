package com.procanchas.email.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash",length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 50)
    @Column(name = "mother_last_name", length = 50)
    private String motherLastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date")
    private ZonedDateTime resetDate = null;

    // RUT
    @Column
    private Integer rutNumber;

    @Column(length = 1)
    private String rutValidator;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String phoneAreaCode;

    @Column
    private String addressNumber;

  //  @ManyToOne
  // private City city;

    @Column
    private String Country;

    @Column
    private String gender;

    @Column
    private LocalDate birthday;

    @Column
    private String facebookId;

    @Column
    private String googleId;


    //@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,mappedBy="user")
    //private Set<UserType> userTypes =  new HashSet<>();

    @Column
    private Boolean fromTemporal;
}
