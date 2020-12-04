package com.example.uorders.exception;

public class LoginFailException extends RuntimeException{
    public LoginFailException(String Id, String Pw) {
        super("Invalid Id or Pw Exception: Id: " + Id + "   Pw: "+ Pw);
        System.out.println(getMessage());
    }
}
