package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_menu")
@IdClass(OrderMenuId.class)
@Getter @Setter
public class OrderMenu {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;
    private int orderPrice;


    //==연관관계 메서드==//
    public void setMenu(Menu menu) {
        this.menu = menu;
        menu.getOrderMenus().add(this);
    }

    //==생성 메서드//
    public static OrderMenu createOrderMenu(Menu menu, int orderPrice, int count, Order order) {
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);
        orderMenu.setOrder(order);

        return orderMenu;
    }

    //==조회 로직==//
    /**
     * 전체 장바구니 가격 조회
     */
    public int getTotalPrice() { return getOrderPrice() * getCount(); }


}
