package doctor.aysst.www.controller;

import doctor.aysst.www.service.HelloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld(){
        return helloService.sayHello();
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

//    @RequestMapping("")
//    public String index_1(){
//        return "index";
//    }

    @RequestMapping("/404")
    @ResponseBody
    public String handleError(){
        return "404";
    }

}
