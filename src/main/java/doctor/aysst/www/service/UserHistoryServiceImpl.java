package doctor.aysst.www.service;

import doctor.aysst.www.dao.UserHistoryDao;
import doctor.aysst.www.entitys.UserHistory;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserHistoryServiceImpl implements UserHistoryService {

    @Autowired
    private UserHistoryDao userHistoryDao;

    @Override
    public void saveHistory(UserHistory userHistory) {
        userHistoryDao.saveHistory(userHistory);
    }

    @Override
    public JSONObject findHistoryByUsername(String username) {
        List<UserHistory> list = userHistoryDao.findHistoryByUsername(username);
        JSONObject cnc = new JSONObject();
        for (int i=0; i<list.size(); i++) {
            System.out.println(JSONObject.fromObject(list.get(i)));
            cnc.put("problem" + i, JSONObject.fromObject(list.get(i)));
        }
        cnc.put("count", list.size());
        return cnc;
    }

}
