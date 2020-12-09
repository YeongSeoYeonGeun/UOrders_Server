package com.example.uorders.api.constants;

public class ResponseMessage {
    public static final String READ_CAFE_LIST = "홈 화면 조회 성공";
    public static final String READ_CAFE = "매장 상세 조회 성공";
    public static final String UPDATE_CAFE = "매장 정보 변경 성공";
    public static final String READ_MENU = "메뉴 상세 조회 성공";

    public static final String CREATE_CARTMENU = "장바구니 메뉴 추가 성공";
    public static final String READ_CART = "장바구니 조회 성공";
    public static final String INITIALIZE_CART = "장바구니 비우기 성공";
    public static final String DELETE_CARTMENU = "장바구니 메뉴 삭제 성공";

    public static final String CREATE_FAVORITE = "즐겨찾는 매장 등록 성공";
    public static final String READ_FAVORITE = "즐겨찾는 매장 조회 성공";
    public static final String DELETE_FAVORITE = "즐겨찾는 매장 등록 해제 성공";

    public static final String CREATE_ORDER = "주문 추가 성공";
    public static final String READ_ORDER_LIST = "주문 내역 조회 성공";
    public static final String ACCEPT_ORDER = "주문 접수 성공";

    public static final String READ_MYPAGE = "마이페이지 조회 성공";
    public static final String READ_CAFE_REVENUE = "매장 수익 내역 조회 성공";

    public static final String PAY_SUCCESS = "결제 성공";
    public static final String PAY_FAIL = "결제 실패";

    public static final String UPDATE_OWNER_ORDER = "주문 완료 처리";

    public static final String CREATE_MENU = "메뉴 생성 성공";
    public static final String DELETE_MENU = "메뉴 삭제 성공";
    public static final String UPDATE_MENU = "메뉴 수정 성공";

    public static final String UPDATE_USER_LANGUAGE = "언어 변경 성공";
  
    public static final String NOT_FOUND_USER = "userIndex 에 해당하는 값이 유효하지 않습니다. userIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_CAFE = "cafeIndex 에 해당하는 값이 유효하지 않습니다. cafeIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_MENU = "menuIndex 에 해당하는 값이 유효하지 않습니다. menuIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_CARTMENU = "cartMenuIndex 에 해당하는 값이 유효하지 않습니다. cartMenuIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_ORDER = "orderIndex 에 해당하는 값이 유효하지 않습니다. orderIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_ORDERMENU = "orderMenuIndex 에 해당하는 값이 유효하지 않습니다. orderMenuIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_OWNER = "ownerIndex 에 해당하는 값이 유효하지 않습니다. ownerIndex 값을 확인해주세요.";
    
    public static final String NOT_FOUND_VALUE = "유효하지 않은 값이 포함되어있습니다.";

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String CREATE_USER = "회원 등록 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
}
