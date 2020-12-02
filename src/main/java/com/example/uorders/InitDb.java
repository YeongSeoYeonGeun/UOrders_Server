package com.example.uorders;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Cart;
import com.example.uorders.domain.Menu;
import com.example.uorders.domain.User;
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

        private Cafe createCafe(String name, String location, String imageLink){

            Cafe cafe = new Cafe();
            cafe.setName(name);
            cafe.setLocation(location);
            cafe.setImage(imageLink);

            Menu menu1 = createMenu("아메리카노", 1500, name+"_아메리카노 이미지 링크", cafe, true, true);
            Menu menu2 = createMenu("카페 라떼", 1500, name+"_카페 라떼 이미지 링크", cafe, true, true);
            Menu menu3 = createMenu("딸기 케이크", 12000, name+"_딸기 케이크 이미지 링크", cafe, true, true);

            Set<Menu> menuSet = new HashSet<>();

            menuSet.add(menu1);
            menuSet.add(menu2);
            menuSet.add(menu3);

            cafe.setMenuSet(menuSet);

            return cafe;
        }

        private Menu createMenu(String name, int price, String imageLink, Cafe cafe, boolean temperatureSelect, boolean sizeSelect){
            Menu menu = new Menu();
            menu.setName(name);
            menu.setPrice(price);
            menu.setImage(imageLink);
            menu.setCafe(cafe);
            menu.setTemperatureSelect(temperatureSelect);
            menu.setSizeSelect(sizeSelect);

            return menu;
        }

        private final EntityManager em;
        public void dbInit1(){

            User user = createUser("시연");
            em.persist(user);

            Cafe cafe1 = createCafe("남산학사 cafe", "신공학관 1층", "남산학사_cafe 이미지 링크");
            Cafe cafe2 = createCafe("가온누리 cafe", "중앙도서관 입구 옆", "가온누리_cafe 이미지 링크");
            Cafe cafe3 = createCafe("펜도로시 cafe", "중앙도서관 1층", "펜도로시_cafe 이미지 링크");
            Cafe cafe4 = createCafe("쎄리오 cafe", "만해광장 앞", "쎄리오_cafe 이미지 링크");
            Cafe cafe5 = createCafe("혜화 디저트 cafe", "혜화관 7층 옥상", "혜화_디저트_cafe 이미지 링크");
            Cafe cafe6 = createCafe("디초콜릿 cafe", "혜화관 대운동장 사이", "디초콜릿_cafe 이미지 링크");
            Cafe cafe7 = createCafe("보니에 cafe", "사회과학관 2층", "보니에_cafe 이미지 링크");
            Cafe cafe8 = createCafe("두리터 cafe", "학술문화관 지하", "두리터_cafe 이미지 링크");

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
