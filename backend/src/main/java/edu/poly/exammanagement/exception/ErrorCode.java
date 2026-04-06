package edu.poly.exammanagement.exception;


public enum ErrorCode {
    USER_EXISTED( 1001 , "User exists"),
    USERNAME_INVALID(9999,"Tài khoản phải dài hơn or bằng 3 kí tự"),
    PASSW_INVALID(9999,"Mật khẩu phải dài hơn or bằng 6 kí tự"),
    USER_NOT_EXISTED( 1990 , "User not exists"),
    UNAUTHENTICATED(1990 , "Sai mật khẩu");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
