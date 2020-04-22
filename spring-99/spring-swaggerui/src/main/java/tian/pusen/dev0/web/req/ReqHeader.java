package tian.pusen.dev0.web.req;

import org.springframework.context.annotation.Profile;

import java.io.Serializable;
@Profile("dev0")
public class ReqHeader implements Serializable {
    // 分布式追踪
    private String gloabelTraceId;
    // uri
    private String serviceName;
    // step
    private Integer stepNo;

}
