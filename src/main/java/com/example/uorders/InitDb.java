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

        private User createUser(String name, String languageCode){
            User user = User.builder()
                    .cart(null)
                    .name(name)
                    .code(null)
                    .languageCode(languageCode)
                    .build();

            Cart cart = Cart.builder()
                    .user(user)
                    .cafe(null)
                    .cartMenuSet(new HashSet<>())
                    .build();

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

            Menu menu1 = createMenu("아메리카노", 1500, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/americano.jpg", cafe, true, true, MenuStatus.AVAILABLE);
            Menu menu2 = createMenu("카페 라떼", 1500, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/cafelatte.jpg", cafe, true, true, MenuStatus.AVAILABLE);
            Menu menu3 = createMenu("핫초코", 3000, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/hotChoco.jpg", cafe, true, true, MenuStatus.AVAILABLE);
            Menu menu4 = createMenu("바나나 우유", 2000, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/bananaMilk.jpg", cafe, true, true, MenuStatus.AVAILABLE);
            Menu menu5 = createMenu("하루과일", 4000, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/fruit.jpg", cafe, false, false, MenuStatus.AVAILABLE);
            Menu menu6 = createMenu("샌드위치", 5000, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/sandwitch.jpg", cafe, false, false, MenuStatus.AVAILABLE);
            Menu menu7 = createMenu("딸기 케이크", 12000, "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/menu/strawberryCake.jpg", cafe, false, false, MenuStatus.AVAILABLE);

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

        private Owner createOwner(String name, String id_owner, Cafe cafe, String password, String businessNumber, String deviceToken){
            Owner owner = Owner.builder()
                    .id_owner(id_owner)
                    .name(name)
                    .cafe(cafe)
                    .password(password)
                    .businessNumber(businessNumber)
                    .deviceToken(deviceToken)
                    .build();

            return owner;
        }

        private final EntityManager em;
        public void dbInit1(){

            Owner owner1 = createOwner("박종근", "test1",null, "1234", "111-111", null);
            Owner owner2 = createOwner("양시연", "test2", null, "1234", "222-222", null);
            Owner owner3 = createOwner("이선영", "test3",null, "1234", "333-333", null);
            Owner owner4 = createOwner("이영서", "test4",null, "1234", "444-444", null);
            Owner owner5 = createOwner("최고운", "test5",null, "1234", "555-555", null);
            Owner owner6 = createOwner("최윤호", "test6",null, "1234", "666-666", null);
            Owner owner7 = createOwner("최서정", "test7",null, "1234", "777-777", null);
            Owner owner8 = createOwner("이승민", "test8", null, "1234", "888-888", null);

            em.persist(owner1);
            em.persist(owner2);
            em.persist(owner3);
            em.persist(owner4);
            em.persist(owner5);
            em.persist(owner6);
            em.persist(owner7);
            em.persist(owner8);

            User user1 = createUser("시연", "ko");
            User user2 = createUser("종근", "zh");

            em.persist(user1);
            em.persist(user2);

            Cafe cafe1 = createCafe(owner1,"남산학사 cafe", "신공학관 1층", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/namsan.jpg");
            Cafe cafe2 = createCafe(owner2, "가온누리 cafe", "중앙도서관 입구 옆", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/gaonuri.jpg");
            Cafe cafe3 = createCafe(owner3, "펜도로시 cafe", "중앙도서관 1층", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/pandorosi.jpg");
            Cafe cafe4 = createCafe(owner4, "쎄리오 cafe", "만해광장 앞", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/serio.jpg");
            Cafe cafe5 = createCafe(owner5, "혜화 디저트 cafe", "혜화관 7층 옥상", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/hyehwa.jpg");
            Cafe cafe6 = createCafe(owner6, "디초콜릿 cafe", "혜화관 대운동장 사이", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/de_choco.jpg");
            Cafe cafe7 = createCafe(owner7,"보니에 cafe", "사회과학관 2층", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/gru.jpg");
            Cafe cafe8 = createCafe(owner8, "두리터 cafe", "학술문화관 지하", "https://uorders-bucket.s3.ap-northeast-2.amazonaws.com/image/cafe/durito.jpg");

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