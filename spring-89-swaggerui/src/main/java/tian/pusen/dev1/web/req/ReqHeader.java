package tian.pusen.dev1.web.req;

import org.springframework.context.annotation.Profile;

import java.io.Serializable;
@Profile("dev1")
public class ReqHeader implements Serializable {
    // 分布式追踪
    private String gloabelTraceId;
    // uri
    private String serviceName;
    // step
    private Integer stepNo;

}
