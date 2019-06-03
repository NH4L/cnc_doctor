package doctor.aysst.www.dao;


import doctor.aysst.www.entitys.CNCProblem;
import doctor.aysst.www.neo4j.Findanswer;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository("CNCProblemService")
public class CNCProblemDao {

    @Autowired
    Findanswer findanswer = new Findanswer();

    public JSONObject findSolutionById(String brand, String type, String questype, String id) throws IOException {
        JSONObject obj =  findanswer.findid(id, brand, type, questype);
        if (obj != null) {
            obj.put("brand", brand);
            obj.put("type", type);
            obj.put("questype", questype);
            obj.put("id", id);
            return obj;
        } else {
            return null;
        }


    }

    public JSONObject findSolutionByQuestion(String question) throws IOException {
        /// TODO operate neo4j DB
        JSONObject obj = findanswer.find(question);
        if (obj != null) {
            obj.put("question", question);
            return obj;
        } else {
            return null;
        }

    }

}
