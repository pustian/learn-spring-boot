package tian.pusen.dev0.entity;

import org.springframework.context.annotation.Profile;
import tian.pusen.dev0.web.req.ReqBody;

import java.util.Date;
import java.util.List;
@Profile("dev0")
public class User implements ReqBody {
    private int id;
    private String username;
    private String address;
    private Boolean famele;
    private Date birthday;
    private List<Pet> petList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public Boolean getFamele() {
        return famele;
    }

    public void setFamele(Boolean famele) {
        this.famele = famele;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
