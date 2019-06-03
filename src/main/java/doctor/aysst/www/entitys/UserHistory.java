package doctor.aysst.www.entitys;

import com.sun.corba.se.spi.ior.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "history")
public class UserHistory implements Serializable {


//    @Id
//    private ObjectId _id;
    private String username;
    private String brand;
    private String type;
    private String question;
    private String questype;
    private String solution;
    private String time;

//    public void set_id(ObjectId _id) {
//        this._id = _id;
//    }
//    public ObjectId get_id() {
//        return _id;
//    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getBrand() {
        return brand;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestype(String questype) {
        this.questype = questype;
    }
    public String getQuestype() {
        return questype;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    public String getSolution() {
        return solution;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }
}
