package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(FavoriteId.class)
@Table(name = "FAVORITE")
public class Favorite {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    //== 연관관계 메서드 ==//
    public void setUser(User user){
        this.user = user;
        user.getFavorites().add(this);
    }

    public void setCafe(Cafe cafe){
        this.cafe = cafe;
        cafe.getFavoriteSet().add(this);
    }

    //== 생성 메서드 ==//
    public static Favorite createFavorite(User user, Cafe cafe) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setCafe(cafe);

        return favorite;
    }
}
