package doctor.aysst.www.controller;

import doctor.aysst.www.service.CNCProblemService;
import doctor.aysst.www.utils.Result;
import doctor.aysst.www.utils.ResultUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/cnc", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
public class CNCProblemController {

    @Autowired
    CNCProblemService cncProblemService;


    @RequestMapping("/getsolutionbyid")
    @ResponseBody
    public Result findSolutionById(@RequestParam String brand, @RequestParam String type, @RequestParam String questype, @RequestParam String id) {
        JSONObject cnc = cncProblemService.findSolutionById(brand, type, questype, id);
        return ResultUtils.success(cnc);
    }

    @RequestMapping("/getsolutionbyques")
    @ResponseBody
    public Result findSolutionById(@RequestParam String question) {
        JSONObject cnc = cncProblemService.findSolutionByQuestion(question);
        return ResultUtils.success(cnc);
    }
}