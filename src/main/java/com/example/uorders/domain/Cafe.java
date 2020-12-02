package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "CAFE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    //== 빌더 ==//
    @Builder
    public Cafe(Owner owner, Set<Order> orderSet, Set<Menu> menuSet, Set<Favorite> favoriteSet, Set<Cart> cartSet, String name, String location, String image) {

        this.owner = owner;
        owner.setCafe(this); //== 연관관계 ==//

        this.orderSet = orderSet;
        this.menuSet = menuSet;
        this.favoriteSet = favoriteSet;
        this.cartSet = cartSet;
        this.name = name;
        this.location = location;
        this.image = image;
    }

}
