package doctor.aysst.www.service;

import doctor.aysst.www.dao.UserInfoTestDao;
import doctor.aysst.www.entitys.UserInfoTest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserInfoTestServiceImpl implements UserInfoTestService {

    @Autowired
    private UserInfoTestDao userInfoTestDao;

    @Override
    public void saveUser(UserInfoTest userInfoTest) {
        userInfoTestDao.saveUser(userInfoTest);
    }

    @Override
    public void updateUserSex(UserInfoTest userInfoTest) {
        userInfoTestDao.updateUserSex(userInfoTest);
    }

    @Override
    public UserInfoTest findUserById(Long id) {
        return userInfoTestDao.findUserById(id);
    }

    @Override
    public String findUserByPortraitAddr(String portraitAddr) {
        List<UserInfoTest> list = userInfoTestDao.findUserByPortraitAddr(portraitAddr);
        String str = "";
        for (int i=0; i<list.size(); i++) {
            System.out.println(JSONObject.fromObject(list.get(i)));
            str += JSONObject.fromObject(list.get(i));
        }
        return str;
    }
}
