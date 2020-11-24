package com.example.uorders.domain;

import java.io.Serializable;
import java.util.Objects;

public class CartMenuId implements Serializable {
    private Long cart;
    private Long menu;

    public CartMenuId() {}

    public CartMenuId(Long cart, Long menu) {
        this.cart = cart;
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartMenuId cartMenuId = (CartMenuId) o;
        return cart.equals(cartMenuId.cart) &&
                menu.equals(cartMenuId.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, menu);
    }

}
