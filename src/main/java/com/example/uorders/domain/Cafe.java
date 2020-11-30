package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "CAFE")
public class Cafe {

    @Id @GeneratedValue
    @Column(name = "cafe_id")
    private Long id;

    @OneToOne(mappedBy = "cafe", fetch = FetchType.LAZY)
    private Owner owner;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orderSet = new HashSet<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Menu> menuSet = new HashSet<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<Favorite> favoriteSet = new HashSet<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private Set<Cart>  cartSet = new HashSet<>();

    private String name;

    private String location;

    private String image;
}
