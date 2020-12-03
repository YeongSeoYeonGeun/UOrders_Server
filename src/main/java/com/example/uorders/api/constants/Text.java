package com.example.uorders.api.constants;

public class Text {
    public static String greeting_korean(String name) { return "안녕하세요 " + name + "님!"; }
    public static String greeting_chinese(String name) { return "你好" + name; }

    public static final String mainText_korean = "오늘은 어떤 음료를 주문하시겠어요?";
    public static final String mainText_chinese = "您今天想点什么饮料？";

    public static String addToCart_korean(int count) { return count + "개 담기"; }
    public static String addToCart_chinese(int count) { return count + "个 加入购物车"; }

    public static String orderMenu_korean(int price) { return price + "원 주문하기"; }
    public static String orderMenu_chinese(int price) { return "订购" + price + "韩元"; }

    public static final String nearestCafe_korean = "가까운 매장";
    public static final String nearestCafe_chinese = "最近的商店";

    public static final String favoriteStore_korean = "즐겨찾는 매장";
    public static final String favoriteStore_chiense = "最喜欢的商店";

    public static final String initializeCart_korean = "모두 삭제";
    public static final String initializeCart_chinese = "删除所有";

    public static final String menuDetail_korean = "메뉴 상세";
    public static final String menuDetail_chiense = "菜单详情";

    public static final String selectSize_korean = "사이즈 선택";
    public static final String selectSize_chinese = "选择大小";

    public static final String cart_korean = "장바구니";
    public static final String cart_chinese = "购物车";

    public static final String order_korean = "주문하기";
    public static final String order_chinese = "订购";

    public static final String count_korean = "개";
    public static final String count_chinese = "个";

    public static final String won_korean = "원";
    public static final String won_chienese = "韩元";

    public static final String and_korean = "외";
    public static final String and_chinese = "和其";

    public static final String completeOrder_korean = "주문 완료";
    public static final String completeOrder_chinese = "订单完成";

    public static final String completeOrderText_korean = "가게에서 주문을 접수했습니다";
    public static final String completeOrderText_chinese = "我在商店收到订单";

    public static final String orderDate_korean = "주문일시";
    public static final String orderDate_chinese = "订单日期和时间";

}
