package tian.pusen.dev1.web.resp;

import org.springframework.context.annotation.Profile;
import tian.pusen.err.ErrorCode;

import java.io.Serializable;

@Profile("dev1")
public class Response implements Serializable {
    private RespHeader respHeader = new RespHeader(ErrorCode.OK);
    private RespBody respBody;

    public Response(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public Response(RespBody respBody) {
        this.respBody = respBody;
    }

    public Response(RespHeader respHeader, RespBody respBody) {
        this.respHeader = respHeader;
        this.respBody = respBody;
    }

    public RespHeader getRespHeader() {
        return respHeader;
    }

    public void setRespHeader(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public RespBody getRespBody() {
        return respBody;
    }

    public void setRespBody(RespBody respBody) {
        this.respBody = respBody;
    }
}
