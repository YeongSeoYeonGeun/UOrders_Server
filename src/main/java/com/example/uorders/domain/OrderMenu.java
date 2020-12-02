package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {

    @Id @GeneratedValue
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;
    private int orderPrice;

    @Enumerated(EnumType.STRING)
    private MenuTemperature menuTemperature; // HOT, ICED

    @Enumerated(EnumType.STRING)
    private MenuSize menuSize; // SMALL, REGULAR, LARGE

    private String menuTakeType; // HERE, TO GO

    //==조회 로직==//
    /**
     * 전체 장바구니 가격 조회
     */
    public int getTotalPrice() { return getOrderPrice() * getCount(); }

    //== 빌더 ==//
    @Builder
    public OrderMenu(Order order, Menu menu, int count, int orderPrice, MenuTemperature menuTemperature, MenuSize menuSize, String menuTakeType) {
        this.order = order;
        order.getOrderMenuSet().add(this); //== 연관관계 ==//

        this.menu = menu;
        menu.getOrderMenuSet().add(this); //== 연관관계 ==//

        this.count = count;
        this.orderPrice = orderPrice;
        this.menuTemperature = menuTemperature;
        this.menuSize = menuSize;
        this.menuTakeType = menuTakeType;
    }
}
