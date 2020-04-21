package tian.pusen.dev0.web.req;

import org.springframework.context.annotation.Profile;

import java.io.Serializable;
@Profile("dev0")
public class Requset<T extends ReqBody> implements Serializable {
    private ReqHeader reqHeader;
    private T reqBody;

    public ReqHeader getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(ReqHeader reqHeader) {
        this.reqHeader = reqHeader;
    }

    public T getReqBody() {
        return reqBody;
    }

    public void setReqBody(T reqBody) {
        this.reqBody = reqBody;
    }
}
