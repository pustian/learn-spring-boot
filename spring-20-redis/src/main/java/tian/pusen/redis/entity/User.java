package tian.pusen.redis.entity;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String lastName;
    private String email;
    private Integer gender; //性别 1男  0女
    //    private Integer userId;
    private Integer dId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

//    @Override
//    public String toString() {
//        return "Employee{" +
//                "id=" + id +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", gender=" + gender +
//                ", dId=" + dId +
//                '}';
//    }
}