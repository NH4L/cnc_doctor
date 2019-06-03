package doctor.aysst.www.dao;

import doctor.aysst.www.entitys.UserHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserHistoryService")
public class UserHistoryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveHistory(UserHistory userHistory) {
        mongoTemplate.save(userHistory, "history");
    }


    public List<UserHistory> findHistoryByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.find(query, UserHistory.class, "history");
    }



}
