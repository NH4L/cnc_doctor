package doctor.aysst.www.dao;

import doctor.aysst.www.entitys.UserInfoTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("UserInfoService")
public class UserInfoTestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveUser(UserInfoTest userInfoTest) {
        mongoTemplate.save(userInfoTest, "userInfo");
    }

    public void updateUserSex(UserInfoTest userInfoTest) {
        Query query = new Query(Criteria.where("id").is(userInfoTest.getId()));

        Update update = new Update();
        update.set("sex", userInfoTest.getSex());

        mongoTemplate.updateFirst(query, update, UserInfoTest.class, "userInfo");
    }

    public UserInfoTest findUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        UserInfoTest userInfoTest = mongoTemplate.findOne(query, UserInfoTest.class, "userInfo");
        return userInfoTest;
    }

    public List<UserInfoTest> findUserByPortraitAddr(String portraitAddr) {
        Query query = new Query(Criteria.where("portraitAddr").is(portraitAddr));
//        UserInfoTest userInfo = mongoTemplate.findOne(query, UserInfoTest.class);
//        return userInfo;
        return mongoTemplate.find(query, UserInfoTest.class, "userInfo");
    }

}
