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

    private String id_owner;
    private String password;
    private String name;
    private String businessNumber;
    private String deviceToken;

    //== 빌더 ==//
    @Builder
    public Owner(Cafe cafe, String id_owner, String password, String name, String businessNumber, String deviceToken) {
        this.cafe = cafe;
        this.id_owner = id_owner;
        this.password = password;
        this.name = name;
        this.businessNumber = businessNumber;
        this.deviceToken = deviceToken;
    }
}
