package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String authority;

}
