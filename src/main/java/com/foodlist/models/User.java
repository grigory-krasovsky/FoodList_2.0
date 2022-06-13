package com.foodlist.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private Boolean active;
    private String roles;
}
