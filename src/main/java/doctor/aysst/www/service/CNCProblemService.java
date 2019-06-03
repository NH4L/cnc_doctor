package doctor.aysst.www.service;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface CNCProblemService {

    JSONObject findSolutionById(String brand, String type, String questype, String id);

    JSONObject findSolutionByQuestion(String question);

}
