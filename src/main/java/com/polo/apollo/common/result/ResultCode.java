package com.polo.apollo.common.result;

public enum ResultCode {

    SUCCESS("200"),
    ERROR("1000");

    private String value;

    private String getValue() {
        return this.value;
    }

    ResultCode(String value) {
        this.value = value;
    }

}
