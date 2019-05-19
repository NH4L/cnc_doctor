package doctor.aysst.www.dao.impl;

import doctor.aysst.www.entitys.UserInfo;
import doctor.aysst.www.dao.UserInfoDao;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述: mongoDB DAO 实现
 *
 **/
@Component
public class UserInfoDaoImpl implements UserInfoDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(UserInfo userInfo) {
        mongoTemplate.save(userInfo);
    }

    @Override
    public void updateUserSex(UserInfo userInfo) {
        Query query = new Query(Criteria.where("id").is(userInfo.getId()));

        Update update = new Update();
        update.set("sex", userInfo.getSex());

        mongoTemplate.updateFirst(query, update, UserInfo.class);
    }

    @Override
    public UserInfo findUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        UserInfo userInfo = mongoTemplate.findOne(query, UserInfo.class);
        return userInfo;
    }

    @Override
    public List<UserInfo> findUserByPortraitAddr(String portraitAddr) {
        Query query = new Query(Criteria.where("portraitAddr").is(portraitAddr));
//        UserInfo userInfo = mongoTemplate.findOne(query, UserInfo.class);
//        return userInfo;
        return mongoTemplate.find(query, UserInfo.class);
    }

}
