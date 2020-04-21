package tian.pusen.err;

public enum ErrorCode {
    OK("000000", "Success"),
    ;
    private String code;
    private String message;
    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
