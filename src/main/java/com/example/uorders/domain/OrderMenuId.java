package com.example.uorders.domain;

import java.io.Serializable;
import java.util.Objects;

public class OrderMenuId implements Serializable {
    private Long order;
    private Long menu;

    public OrderMenuId() { }

    public OrderMenuId(Long order,Long menu) {
        this.order = order;
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMenuId orderMenuId = (OrderMenuId) o;
        return order.equals(orderMenuId.order) &&
                menu.equals(orderMenuId.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, menu);
    }
}
