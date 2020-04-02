package tian.pusen.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
