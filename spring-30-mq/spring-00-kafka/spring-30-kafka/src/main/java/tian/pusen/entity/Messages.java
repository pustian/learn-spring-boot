package tian.pusen.entity;

import java.io.Serializable;
import java.util.Date;

public class Messages  implements Serializable {
    private Long id;
    private String msg;
    private Date sendTime;
    /**
     * transient 关键字修饰的字段不会被序列化
     */
    private transient String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
