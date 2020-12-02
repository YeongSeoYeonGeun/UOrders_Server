package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_menu")
@Getter @Setter
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

    //==연관관계 메서드==//
    public void setMenu(Menu menu) {
        this.menu = menu;
        menu.getOrderMenuSet().add(this);
    }

    //==생성 메서드//
    public static OrderMenu createOrderMenu(Menu menu, int orderPrice, int count, MenuTemperature menuTemperature, MenuSize menuSize, String menuTakeType) {
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);
        orderMenu.setMenuTemperature(menuTemperature);
        orderMenu.setMenuSize(menuSize);
        orderMenu.setMenuTakeType(menuTakeType);

        return orderMenu;
    }

    //==조회 로직==//
    /**
     * 전체 장바구니 가격 조회
     */
    public int getTotalPrice() { return getOrderPrice() * getCount(); }


}
