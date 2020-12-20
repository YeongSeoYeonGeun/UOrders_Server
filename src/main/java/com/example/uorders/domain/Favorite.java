package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(FavoriteId.class)
@Table(name = "FAVORITE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    //== 빌더 ==//
    @Builder
    public Favorite(User user, Cafe cafe) {
        this.user = user;
        user.getFavoriteSet().add(this);
        this.cafe = cafe;
        cafe.getFavoriteSet().add(this);
    }
}
