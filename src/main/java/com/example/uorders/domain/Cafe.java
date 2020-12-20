package com.example.uorders.domain;

import com.example.uorders.util.Translator;
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
    private String name_english;
    private String name_chinese;

    private String location;
    private String location_english;
    private String location_chinese;

    private String image;

    public String getName(String languageCode) {

        switch (languageCode){
            case "en":
                return this.name_english;
            case "zh":
                return this.name_chinese;
            default:
                return this.name;
        }
    }

    public String getLocation(String languageCode) {

        switch (languageCode){
            case "en":
                return this.location_english;
            case "zh":
                return this.location_chinese;
            default:
                return this.location;
        }
    }

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
        this.name_chinese = Translator.translate(name, "zh");
        this.name_english = Translator.translate(name, "en");
        this.location = location;
        this.location_chinese = Translator.translate(location, "zh");
        this.location_english = Translator.translate(location, "en");
        this.image = image;
    }

}
