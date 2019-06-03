package doctor.aysst.www.controller;

import doctor.aysst.www.service.UserInfoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserTestController {

    @Autowired
    UserInfoTestService userInfoService;


    @RequestMapping("/user")
    @ResponseBody
    public String findUserByPortraitAddrTest() {
        return userInfoService.findUserByPortraitAddr("https://www.baidu.com");
    }
}