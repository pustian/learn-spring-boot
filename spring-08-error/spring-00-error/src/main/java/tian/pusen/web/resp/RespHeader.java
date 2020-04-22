package tian.pusen.web.resp;

import tian.pusen.err.ServiceCode;

import java.io.Serializable;


public class RespHeader implements Serializable {
    private String code;
    private String message;

    public RespHeader(ServiceCode errorCode) {
        code = errorCode.getCode();
        message = errorCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
