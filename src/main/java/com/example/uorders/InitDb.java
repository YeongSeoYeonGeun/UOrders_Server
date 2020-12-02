package com.example.uorders;

import com.example.uorders.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 *  유저 1명
 *  카페 8개
 *  인덱스, 이름, 장소, 이미지
 *
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private User createUser(String name){
            User user = User.builder()
                    .cart(null)
                    .name(name)
                    .code(null)
                    .build();

            Cart cart = Cart.builder()
                    .user(user)
                    .cafe(null)
                    .cartMenuSet(new HashSet<>())
                    .build();

            em.persist(cart);
            return user;
        }

        private Cafe createCafe(Owner owner, String name, String location, String imageLink){

            Cafe cafe = Cafe.builder()
                    .owner(owner)
                    .cartSet(new HashSet<>())
                    .favoriteSet(new HashSet<>())
                    .menuSet(new HashSet<>())
                    .orderSet(new HashSet<>())
                    .name(name)
                    .location(location)
                    .image(imageLink)
                    .build();

            Menu menu1 = createMenu("아메리카노", 1500, name+"_아메리카노 이미지 링크", cafe, true, true, MenuStatus.AVAILABLE);
            Menu menu2 = createMenu("카페 라떼", 1500, name+"_카페 라떼 이미지 링크", cafe, true, true, MenuStatus.AVAILABLE);
            Menu menu3 = createMenu("딸기 케이크", 12000, name+"_딸기 케이크 이미지 링크", cafe, false, true, MenuStatus.AVAILABLE);

            return cafe;
        }

        private Menu createMenu(String name, int price, String imageLink, Cafe cafe, boolean temperatureSelect, boolean sizeSelect, MenuStatus menuStatus){
            Menu menu = Menu.builder()
                    .name(name)
                    .price(price)
                    .cafe(cafe)
                    .image(imageLink)
                    .cartMenuSet(new HashSet<>())
                    .orderMenuSet(new HashSet<>())
                    .sizeSelect(sizeSelect)
                    .temperatureSelect(temperatureSelect)
                    .status(menuStatus)
                    .build();
            return menu;
        }

        private Owner createOwner(String name, Cafe cafe, String password, String businessNumber){
            Owner owner = Owner.builder()
                    .name(name)
                    .cafe(cafe)
                    .password(password)
                    .businessNumber(businessNumber)
                    .build();

            return owner;
        }

        private final EntityManager em;
        public void dbInit1(){

            Owner owner1 = createOwner("박종근", null, "1234", "111-111");
            Owner owner2 = createOwner("양시연", null, "1234", "222-222");
            Owner owner3 = createOwner("이선영", null, "1234", "333-333");
            Owner owner4 = createOwner("이영서", null, "1234", "444-444");
            Owner owner5 = createOwner("최고운", null, "1234", "555-555");
            Owner owner6 = createOwner("최윤호", null, "1234", "666-666");
            Owner owner7 = createOwner("최서정", null, "1234", "777-777");
            Owner owner8 = createOwner("이승민", null, "1234", "888-888");

            em.persist(owner1);
            em.persist(owner2);
            em.persist(owner3);
            em.persist(owner4);
            em.persist(owner5);
            em.persist(owner6);
            em.persist(owner7);
            em.persist(owner8);

            User user = createUser("시연");
            em.persist(user);

            Cafe cafe1 = createCafe(owner1,"남산학사 cafe", "신공학관 1층", "남산학사_cafe 이미지 링크");
            Cafe cafe2 = createCafe(owner2, "가온누리 cafe", "중앙도서관 입구 옆", "가온누리_cafe 이미지 링크");
            Cafe cafe3 = createCafe(owner3, "펜도로시 cafe", "중앙도서관 1층", "펜도로시_cafe 이미지 링크");
            Cafe cafe4 = createCafe(owner4, "쎄리오 cafe", "만해광장 앞", "쎄리오_cafe 이미지 링크");
            Cafe cafe5 = createCafe(owner5, "혜화 디저트 cafe", "혜화관 7층 옥상", "혜화_디저트_cafe 이미지 링크");
            Cafe cafe6 = createCafe(owner6, "디초콜릿 cafe", "혜화관 대운동장 사이", "디초콜릿_cafe 이미지 링크");
            Cafe cafe7 = createCafe(owner7,"보니에 cafe", "사회과학관 2층", "보니에_cafe 이미지 링크");
            Cafe cafe8 = createCafe(owner8, "두리터 cafe", "학술문화관 지하", "두리터_cafe 이미지 링크");

            em.persist(cafe1);
            em.persist(cafe2);
            em.persist(cafe3);
            em.persist(cafe4);
            em.persist(cafe5);
            em.persist(cafe6);
            em.persist(cafe7);
            em.persist(cafe8);
        }

    }
}