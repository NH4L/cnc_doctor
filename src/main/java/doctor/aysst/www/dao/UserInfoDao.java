package doctor.aysst.www.dao;

import doctor.aysst.www.entitys.UserInfo;

import java.util.List;

/**
 * 描述: 提供增删改查 MongoDB 接口
 **/
public interface UserInfoDao {

    void saveUser(UserInfo userInfo);

    void updateUserSex(UserInfo userInfo);

    UserInfo findUserById(Long id);

    List<UserInfo> findUserByPortraitAddr(String portraitAddr);
}
