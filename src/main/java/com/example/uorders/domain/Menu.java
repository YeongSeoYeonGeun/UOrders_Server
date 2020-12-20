package com.example.uorders.domain;

import com.example.uorders.util.Translator;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "MENU")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private Set<CartMenu> cartMenuSet = new HashSet<>();

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private Set<OrderMenu> orderMenuSet = new HashSet<>();

    private String name;
    private String name_english;
    private String name_chinese;

    private int price;
    private String image;

    @Column(columnDefinition = "boolean default true")
    private boolean temperatureSelect;

    @Column(columnDefinition = "boolean default true")
    private boolean sizeSelect;

    @Enumerated(EnumType.STRING)
    private MenuStatus status; // AVAILABLE, UNAVAILABLE

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

    public boolean getTemperatureSelect() { return this.temperatureSelect; }

    public void setTemperatureSelect(boolean value) { this.temperatureSelect = value; }

    public boolean getSizeSelect() { return this.sizeSelect; }

    public void setSizeSelect(boolean value) { this.sizeSelect = value; }

    @Builder
    public Menu(Cafe cafe, Set<CartMenu> cartMenuSet, Set<OrderMenu> orderMenuSet, String name, int price, String image, boolean temperatureSelect, boolean sizeSelect, MenuStatus status) {

        this.cafe = cafe;
        cafe.getMenuSet().add(this); //==  연관관계 설정 ==//

        this.cartMenuSet = cartMenuSet;
        this.orderMenuSet = orderMenuSet;
        this.name = name;
        this.name_chinese = Translator.translate(name, "zh");
        this.name_english = Translator.translate(name, "en");
        this.price = price;
        this.image = image;
        this.temperatureSelect = temperatureSelect;
        this.sizeSelect = sizeSelect;
        this.status = status;
    }
}
