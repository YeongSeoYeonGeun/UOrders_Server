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
@Table(name = "MENU")
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private Set<CartMenu> cartMenus = new HashSet<>();

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private Set<OrderMenu> orderMenus = new HashSet<>();

    private String name;

    private int price;

    private String image;

    @Enumerated(EnumType.STRING)
    private MenuStatus status; // AVAILABLE, UNAVAILABLE
}
