package doctor.aysst.www;

import doctor.aysst.www.entitys.UserInfo;
import doctor.aysst.www.dao.UserInfoDao;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 描述: mongoDB测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CncDoctorSpringbootApplication.class)
public class SpringBootMongodbApplicationTests {

    @Autowired
    private UserInfoDao userInfoDao;

    @Test
    public void saveUserTest() {

//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(10001L);
//        userInfo.setUsername("小让");
//        userInfo.setPassword("123456");
//        userInfo.setSex("男");
//        userInfo.setEmail("xxx@qq.com");
//        userInfo.setPhone("1524131411");
//        userInfo.setSignature("nice day");
//        userInfo.setPortraitAddr("https://www.baidu.com");
//        userInfoDao.saveUser(userInfo);

//        userInfo = new UserInfo();
//        userInfo.setId(10002L);
//        userInfo.setUsername("大川");
//        userInfo.setPassword("12345678");
//        userInfo.setSex("女");
//        userInfo.setEmail("xxxx@qq.com");
//        userInfo.setPhone("313131411");
//        userInfo.setSignature("nice day! 开心");
//        userInfo.setPortraitAddr("https://www.baidu.com");
//        userInfoDao.saveUser(userInfo);
    }


    @Test
    public void updateUserSexTest() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(10002L);
//        userInfo.setSex("女");
//
//        userInfoDao.updateUserSex(userInfo);
    }

    @Test
    public void findUserByIdTest() {

//        UserInfo userInfo = userInfoDao.findUserById(10002L);
//        System.out.println(JSONObject.fromObject(userInfo));
    }

    @Test
    public void findUserByPortraitAddrTest  () {

        List<UserInfo> list = userInfoDao.findUserByPortraitAddr("https://www.baidu.com");
        for (int i=0; i<list.size(); i++)
            System.out.println(JSONObject.fromObject(list.get(i)));
    }

}
