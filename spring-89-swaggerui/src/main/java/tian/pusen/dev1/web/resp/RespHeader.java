package tian.pusen.dev1.web.resp;

import org.springframework.context.annotation.Profile;
import tian.pusen.err.ErrorCode;

import java.io.Serializable;
@Profile("dev1")
public class RespHeader implements Serializable {
    private String code;
    private String message;

    public RespHeader(ErrorCode errorCode) {
        code = errorCode.OK.getCode();
        message = errorCode.OK.getMessage();
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
