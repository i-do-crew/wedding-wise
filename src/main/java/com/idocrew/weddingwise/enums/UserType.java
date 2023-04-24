package com.idocrew.weddingwise.enums;

public enum UserType {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER"),
    VENDOR("VENDOR");

    private String code;

    UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
