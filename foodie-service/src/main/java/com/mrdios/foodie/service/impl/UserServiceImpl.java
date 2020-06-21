package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.common.enums.SexEnum;
import com.mrdios.foodie.common.utils.MD5Utils;
import com.mrdios.foodie.mapper.UsersMapper;
import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.pojo.bo.UserBo;
import com.mrdios.foodie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUsernameExist(String username) {
        Example example = new Example(Users.class);
        example.createCriteria().andEqualTo("username", username);
        return usersMapper.selectOneByExample(example) != null;
    }

    @Override
    public Users createUser(UserBo userBo) {
        Users user = new Users();
        user.setId(sid.nextShort());
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            log.error("用户名密码设置异常", e);
            throw new RuntimeException("密码设置异常");
        }
        user.setNickname(user.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(Date.from(LocalDate.parse("1900-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant()));
        user.setSex(SexEnum.SECRET.getCode());
        user.setCreatedTime(new java.util.Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }

    @Override
    public Users getUserForLogin(UserBo userBo) {
        Example example = new Example(Users.class);
        example.createCriteria().andEqualTo("username", userBo.getUsername())
                .andEqualTo("password", userBo.getPassword());
        return usersMapper.selectOneByExample(example);
    }
}
