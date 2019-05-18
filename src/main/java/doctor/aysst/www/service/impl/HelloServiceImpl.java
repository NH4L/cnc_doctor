package doctor.aysst.www.service.impl;

import doctor.aysst.www.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "Hello World!";
    }
}