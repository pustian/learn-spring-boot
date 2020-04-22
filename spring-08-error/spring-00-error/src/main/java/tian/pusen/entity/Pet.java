package tian.pusen.entity;

import tian.pusen.web.resp.RespBody;

import java.io.Serializable;

public class Pet implements RespBody, Serializable {
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
