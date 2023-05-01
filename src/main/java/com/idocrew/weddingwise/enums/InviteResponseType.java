package com.idocrew.weddingwise.enums;

public enum InviteResponseType {
    INVITED("INVITED"),
    RSVP("RSVP"),
    DECLINED("DECLINED");

    private String code;

    InviteResponseType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
