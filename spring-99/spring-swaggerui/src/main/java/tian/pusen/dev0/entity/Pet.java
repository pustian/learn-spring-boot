package tian.pusen.dev0.entity;

import org.springframework.context.annotation.Profile;
import tian.pusen.dev0.web.req.ReqBody;
import tian.pusen.dev0.web.resp.RespBody;
@Profile("dev0")
public class Pet implements ReqBody, RespBody {
    private Integer id;
    private String special;
    private String name;
    private Double weight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
