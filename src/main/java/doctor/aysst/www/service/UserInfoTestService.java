package doctor.aysst.www.service;

import doctor.aysst.www.entitys.UserInfoTest;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoTestService {

    void saveUser(UserInfoTest userInfoTest);

    void updateUserSex(UserInfoTest userInfoTest);

    UserInfoTest findUserById(Long id);

    String findUserByPortraitAddr(String portraitAddr);
}
