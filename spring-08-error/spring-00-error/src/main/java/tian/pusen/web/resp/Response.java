package tian.pusen.web.resp;

import tian.pusen.err.ServiceCode;

import java.io.Serializable;

public class Response<T extends RespBody> implements Serializable {
    private RespHeader respHeader = new RespHeader(ServiceCode.OK);;
    private T respBody;

    public Response() {
    }

    public Response(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public Response(T respBody) {
        respHeader = new RespHeader(ServiceCode.OK);
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
