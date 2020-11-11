package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Cafe {

    @Id @GeneratedValue
    @Column(name = "cafe_id")
    private Long id;

    @OneToOne(mappedBy = "cafe", fetch = FetchType.LAZY)
    private Owner owner;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Menu> menu = new ArrayList<>();

    private String name;

    private String location;

    // Image
    // private ?? image;
}
