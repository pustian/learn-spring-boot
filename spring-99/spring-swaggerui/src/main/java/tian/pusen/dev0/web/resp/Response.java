package tian.pusen.dev0.web.resp;

import org.springframework.context.annotation.Profile;
import tian.pusen.err.ErrorCode;

import java.io.Serializable;
@Profile("dev0")
public class Response<T extends RespBody> implements Serializable {
    private RespHeader respHeader = new RespHeader(ErrorCode.OK);
    private T respBody;

    public Response(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public Response(T respBody) {
        this.respBody = respBody;
    }

    public Response(RespHeader respHeader, T respBody) {
        this.respHeader = respHeader;
        this.respBody = respBody;
    }

    public RespHeader getRespHeader() {
        return respHeader;
    }

    public void setRespHeader(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public T getRespBody() {
        return respBody;
    }

    public void setRespBody(T respBody) {
        this.respBody = respBody;
    }
}
