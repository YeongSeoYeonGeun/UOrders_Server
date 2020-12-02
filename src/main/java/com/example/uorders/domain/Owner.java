package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "OWNER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

    @Id @GeneratedValue
    @Column(name = "owner_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private String password;

    private String name;

    private String businessNumber;

    //== 빌더 ==//
    @Builder
    public Owner(Cafe cafe, String password, String name, String businessNumber) {
        this.cafe = cafe;
        this.password = password;
        this.name = name;
        this.businessNumber = businessNumber;
    }
}
