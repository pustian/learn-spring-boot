package tian.pusen.dev1.web.req;

import org.springframework.context.annotation.Profile;

import java.io.Serializable;
@Profile("dev1")
public class Requset<T extends ReqBody> implements Serializable {
    private ReqHeader reqHeader;
    private ReqBody reqBody;

    public ReqHeader getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(ReqHeader reqHeader) {
        this.reqHeader = reqHeader;
    }

    public ReqBody getReqBody() {
        return reqBody;
    }

    public void setReqBody(ReqBody reqBody) {
        this.reqBody = reqBody;
    }
}
