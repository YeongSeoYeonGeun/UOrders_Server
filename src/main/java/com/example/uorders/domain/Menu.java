package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "menu")
    private List<CartMenu> cartMenu = new ArrayList<>();

    private String name;

    private int price;

    // Image
    // private ?? image;

    @Enumerated(EnumType.STRING)
    private MenuStatus status; // AVAILABLE, UNAVAILABLE

    @Enumerated(EnumType.STRING)
    private MenuTemperature temperature; // HOT, ICED

    @Enumerated(EnumType.STRING)
    private MenuSize size; // SMALL, REGULAR, LARGE

    @Enumerated(EnumType.STRING)
    private MenuPacking packing; // HERE, TO_GO
}
