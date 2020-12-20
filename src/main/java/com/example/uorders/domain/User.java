package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String code;

    private String languageCode;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orderSet = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Favorite> favoriteSet = new HashSet<>();

    //== 빌더 ==//
    @Builder
    public User(String name, String code, Set<Order> orderSet, Cart cart, Set<Favorite> favoriteSet, String languageCode){

        this.name = name;
        this.code = code;
        this.orderSet = orderSet;
        this.cart = cart;
        this.favoriteSet = favoriteSet;
        this.languageCode = languageCode;
    }
}
