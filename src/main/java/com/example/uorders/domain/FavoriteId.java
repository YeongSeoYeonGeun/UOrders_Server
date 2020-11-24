package com.example.uorders.domain;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {
    private Long user;
    private Long cafe;

    public FavoriteId() { }

    public FavoriteId(Long user,Long cafe) {
        this.user = user;
        this.cafe = cafe;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId favoriteId = (FavoriteId) o;
        return user.equals(favoriteId.user) &&
                cafe.equals(favoriteId.cafe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, cafe);
    }

}
