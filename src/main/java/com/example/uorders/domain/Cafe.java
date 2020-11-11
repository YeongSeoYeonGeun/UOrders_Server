package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Cafe {

    @Id @GeneratedValue
    @Column(name = "cafe_id")
    private Long id;

    private String name;

    private String location;

    // Image
    // private ?? image;
}
