package doctor.aysst.www.service;

import doctor.aysst.www.dao.CNCProblemDao;
import doctor.aysst.www.entitys.CNCProblem;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class CNCProblemServiceImpl implements CNCProblemService{

    @Autowired
    CNCProblemDao cncProblemDao;

    @Override
    public JSONObject findSolutionById(String brand, String type, String questype, String id) {
        JSONObject obj = null;
        try {       //String quesid, String brand, String machinetype, String questype
            obj = cncProblemDao.findSolutionById(brand, type, questype, id);
            if (obj != null) {
                System.out.println(obj.toString());
                return obj;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JSONObject findSolutionByQuestion(String question) {
        JSONObject obj = null;
        try {
            obj = cncProblemDao.findSolutionByQuestion(question);
            if (obj != null) {
                System.out.println(obj.toString());
                return obj;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
