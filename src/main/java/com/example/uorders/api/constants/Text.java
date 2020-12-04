package com.example.uorders.api.constants;

public class Text {
    public static String greeting(String name, String languageCode) {
        switch (languageCode){
            case "zh":
                return "你好" + name;
            default:
                return "안녕하세요 " + name + "님!";
        }
    }

    public static String main(String languageCode) {
        switch (languageCode){
            case "zh":
                return "您今天想点什么饮料？";
            default:
                return "오늘은 어떤 음료를 주문하시겠어요?";
        }
    }

    public static String addToCart(String languageCode) {
        switch (languageCode){
            case "zh":
                return "个 加入购物车";
            default:
                return "개 담기";
        }
    }

    public static String payMenu(int price, String languageCode) {
        switch (languageCode){
            case "zh":
                return "支付" + price + "韩元";
            default:
                return price + "결제하기";
        }
    }

    public static String nearestCafe(String languageCode) {
        switch (languageCode){
            case "zh":
                return "最近的商店";
            default:
                return "가까운 매장";
        }
    }

    public static String favoriteCafe(String languageCode) {
        switch (languageCode){
            case "zh":
                return "最喜欢的商店";
            default:
                return "즐겨찾는 매장";
        }
    }

    public static String initializeCart(String languageCode) {
        switch (languageCode){
            case "zh":
                return "删除所有";
            default:
                return "모두 삭제";
        }
    }

    public static String menuDetail(String languageCode) {
        switch (languageCode){
            case "zh":
                return "菜单详情";
            default:
                return "메뉴 상세";
        }
    }

    public static String selectSize(String languageCode) {
        switch (languageCode){
            case "zh":
                return "选择大小";
            default:
                return "사이즈 선택";
        }
    }

    public static String cart(String languageCode) {
        switch (languageCode){
            case "zh":
                return "购物车";
            default:
                return "장바구니";
        }
    }

    public static String order(String languageCode) {
        switch (languageCode){
            case "zh":
                return "订购";
            default:
                return "주문하기";
        }
    }

    public static String count(String languageCode) {
        switch (languageCode){
            case "zh":
                return "个";
            default:
                return "개";
        }
    }

    public static String completeOrder(String languageCode) {
        switch (languageCode){
            case "zh":
                return "订单完成";
            default:
                return "주문 완료";
        }
    }

    public static String acceptOrder(String languageCode) {
        switch (languageCode){
            case "zh":
                return "我在商店收到订单";
            default:
                return "가게에서 주문을 접수했습니다";
        }
    }

    public static String orderDate(String languageCode) {
        switch (languageCode){
            case "zh":
                return "订单日期和时间";
            default:
                return "주문일시";
        }
    }

    public static String menuPrice(int count, int price, String languageCode) {
        switch (languageCode){
            case "zh":
                return count + "个" + price + "韩元";
            default:
                return count + "개 " + price + "원";
        }
    }

    public static String menuPrice(int price, String languageCode) {
        switch (languageCode){
            case "zh":
                return price + "韩元";
            default:
                return price + "원";
        }
    }

    public static String won(String languageCode) {
        switch (languageCode){
            case "zh":
                return "韩元";
            default:
                return "원";
        }
    }

    public static String readOrder(String languageCode) {
        switch (languageCode){
            case "zh":
                return "查看订单历史";
            default:
                return "주문 내역 확인";
        }
    }


    public static String totalPrice(String languageCode) {
        switch (languageCode){
            case "zh":
                return " 总付款额";
            default:
                return "총 결제금액";
        }
    }

    public static String year(String languageCode) {
        switch (languageCode){
            case "zh":
                return "年";
            default:
                return "년";
        }
    }

    public static String month(String languageCode) {
        switch (languageCode){
            case "zh":
                return "月";
            default:
                return "월";
        }
    }

    public static String day(String languageCode) {
        switch (languageCode){
            case "zh":
                return "日";
            default:
                return "일";
        }
    }

    public static String morning(String languageCode) {
        switch (languageCode){
            case "zh":
                return "早上";
            default:
                return "오전";
        }
    }

    public static String afternoon(String languageCode) {
        switch (languageCode){
            case "zh":
                return "下午";
            default:
                return "오후";
        }
    }

    public static String and(String languageCode) {
        switch (languageCode){
            case "zh":
                return "和其";
            default:
                return "외";
        }
    }

    public static String number(String languageCode) {
        switch (languageCode){
            case "zh":
                return "个";
            default:
                return "개";
        }
    }

}
