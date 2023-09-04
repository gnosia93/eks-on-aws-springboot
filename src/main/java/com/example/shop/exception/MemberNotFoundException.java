package com.example.shop.exception;

public class MemberNotFoundException extends RuntimeException {
    public String getMessage() {
        return "MemberNotFoundException";
    }
}
