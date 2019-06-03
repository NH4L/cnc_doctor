package doctor.aysst.www.controller;


import doctor.aysst.www.entitys.UserHistory;
import doctor.aysst.www.service.UserHistoryService;
import doctor.aysst.www.utils.Result;
import doctor.aysst.www.utils.ResultUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cnc", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
public class UserHistoryController {

    @Autowired
    UserHistoryService userHistoryService;

    @RequestMapping("/addhistory")
    @ResponseBody
    public Result saveHistory(@RequestParam String historyJson) {
//        String historyJson = "{\"username\" : \"lcy\",\"brand\" : \"西门子\",\"question\" : \"该报警可以显示内部报警状态，同时传递报警数量，提供了有关报警原因和报警位置方面的信息。\",\"solution\" : \"记录错误文本并且与 Siemens AG、 A & D MC 服务热线联系( 电话 / 传真参看报警 1000）。用清除键或NC- 启动 键清除报警\",\"type\" : \"840D/840Di/810D\",\"questype\" : \"NCK 报警\"}";
        JSONObject objectHistory =  JSONObject.fromObject(historyJson);
        UserHistory userHistory = (UserHistory) JSONObject.toBean(objectHistory, UserHistory.class);
        userHistoryService.saveHistory(userHistory);
        return ResultUtils.success();
    }


    @RequestMapping("/gethistory")
    @ResponseBody
    public Result findSolutionById(@RequestParam String username) {
        JSONObject cnc = userHistoryService.findHistoryByUsername(username);
        return ResultUtils.success(cnc);
    }

}