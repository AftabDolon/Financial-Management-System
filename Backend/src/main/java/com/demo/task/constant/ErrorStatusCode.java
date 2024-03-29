package com.demo.task.constant;

import java.io.Serializable;

public enum ErrorStatusCode implements Serializable {
    COMMON_SERVER_ERROR("AN UNEXPECTED ERROR OCCURRED, PLEASE TRY AGAIN LATER!", 99),

    //UPDATE_OPERATION
    NOT_PROVIDE_ID("BAD REQUEST, USER ID REQUIRED FOR UPDATE OPERATION", 417),

    // Authentication error
    JWT_TOKEN_EXPIRED("YOUR TOKEN HAS EXPIRED, PLEASE LOGIN AGAIN", 801),
    USER_NOT_ACTIVE("INVALID USER OR USER NOT ACTIVE", 809),
    INVALID_USER_PASSWORD("INVALID USER ID OR PASSWORD", 901),
    INVALID_USER("SORRY! INVALID USER", 904),
    NOT_AUTHORIZED("SORRY! YOU ARE NOT AUTHORIZED", 905),
    INVALID_EMAIL("SORRY! INVALID EMAIL ADDRESS", 906),

    CURRENT_NEW_PASSWORD_NOT_BLANK("CURRENT PASSWORD AND NEW PASSWORD CAN NOT BE EMPTY", 907),
    PASSWORD_NOT_6_CHAR_LONG("PASSWORD SHOULD CONTAIN AT LEAST 6 CHARACTER", 908),
    CURRENT_PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCHED("CURRENT PASSWORD AND CONFIRM PASSWORD NOT MATCHED", 909),
    PASSWORD_AND_NEW_PASSWORD_CAN_NOT_SAME("CURRENT PASSWORD AND NEW PASSWORD SHOULD NOT BE SAME", 910),
    CURRENT_PASSWORD_NOT_MATCH("CURRENT PASSWORD NOT MATCH", 911),
    OTP_NOT_MATCH("ENTER OTP IS INVALID OR EXPIRED", 912);




    private final String value;
    private final Integer code;

    ErrorStatusCode(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}
