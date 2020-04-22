package tian.pusen.dev0.web.resp;

import org.springframework.context.annotation.Profile;
import tian.pusen.err.ErrorCode;

import java.io.Serializable;
import java.util.Collection;

@Profile("dev0")
public class RespCollections<T extends RespBody> implements Serializable {
    private RespHeader respHeader = new RespHeader(ErrorCode.OK);
    private Collection respBody;

    public RespCollections(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public RespCollections(Collection respBody) {
        this.respBody = respBody;
    }

    public RespCollections(RespHeader respHeader, Collection respBody) {
        this.respHeader = respHeader;
        this.respBody = respBody;
    }

    public RespHeader getRespHeader() {
        return respHeader;
    }

    public void setRespHeader(RespHeader respHeader) {
        this.respHeader = respHeader;
    }

    public Collection getRespBody() {
        return respBody;
    }

    public void setRespBody(Collection respBody) {
        this.respBody = respBody;
    }
}
