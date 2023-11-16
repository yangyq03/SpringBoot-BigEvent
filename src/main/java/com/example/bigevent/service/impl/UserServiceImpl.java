package com.example.bigevent.service.impl;

import com.example.bigevent.Utils.Md5Util;
import com.example.bigevent.Utils.ThreadLocalUtil;
import com.example.bigevent.mapper.UserMapper;
import com.example.bigevent.pojo.User;
import com.example.bigevent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        //使用md5对密码进行加密
        String md5String = Md5Util.getMD5String(password);

        //添加用户
        userMapper.add(username, md5String);

    }

    @Override
    public void update(User user) {
        //更新更新时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        //通过ThreadLocal获取创建者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, userId);
    }

    @Override
    public void updatePwd(String newPwd) {
        //通过ThreadLocal获取创建者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), userId);
    }
}
