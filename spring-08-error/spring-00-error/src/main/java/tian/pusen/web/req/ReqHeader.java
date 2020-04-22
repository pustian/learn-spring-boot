package tian.pusen.web.req;

import java.io.Serializable;

public class ReqHeader implements Serializable {
    // 分布式追踪
    private String gloabelTraceId;
    // uri
    private String serviceName;
    // step
    private Integer stepNo;

}
