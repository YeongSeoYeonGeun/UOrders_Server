package com.example.uorders.api;

public class ResponseMessage {
    public static final String READ_CAFE_LIST = "홈 화면 조회 성공";
    public static final String READ_CAFE = "매장 상세 조회 성공";
    public static final String CREATE_CARTMENU = "장바구니 메뉴 추가 성공";
    public static final String READ_CARTMENU = "장바구니 메뉴 조회 성공";
    public static final String DELETE_CARTMENU = "장바구니 메뉴 삭제 성공";
    public static final String SET_FAVORITE = "즐겨찾는 매장 등록 성공";
    public static final String READ_FAVORITE = "즐겨찾는 매장 조회 성공";


    public static final String NOT_FOUND_USER = "userIndex에 해당하는 값이 유효하지 않습니다. userIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_CAFE = "cafeIndex에 해당하는 값이 유효하지 않습니다. cafeIndex 값을 확인해주세요.";
    public static final String NOT_FOUND_MENU = "menuIndex에 해당하는 값이 유효하지 않습니다. menuIndex 값을 확인해주세요.";

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String CREATE_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
}
