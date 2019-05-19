package doctor.aysst.www.entitys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "userInfo")
public class UserInfo implements Serializable {

    @Id
    private Long id;

    private String username;
    private String sex;
    private String email;
    private String password;
    private String phone;
    private String signature;
    private String portraitAddr;
    public UserInfo(){

    }
    public UserInfo(String username) {
        this.username = username;
    }

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserInfo(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getSignature() {
        return signature;
    }

    public String getPortraitAddr() {
        return portraitAddr;
    }
    public void setPortraitAddr(String portraitAddr) {
        this.portraitAddr = portraitAddr;
    }

}
