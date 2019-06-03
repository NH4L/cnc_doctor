package doctor.aysst.www.service;

import doctor.aysst.www.entitys.UserHistory;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserHistoryService {

    JSONObject findHistoryByUsername(String username);

    void saveHistory(UserHistory userHistory);

}
