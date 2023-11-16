package com.example.bigevent.mapper;

import com.example.bigevent.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //根据用户名来查找用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    //添加用户
    @Insert("insert into user(username, password, create_time, update_time) " +
            "values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    //更新
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    //更新头像
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{userId}")
    void updateAvatar(String avatarUrl, Integer userId);

    //更新用户密码
    @Update("update user set password=#{newPwd},update_time=now() where id=#{userId}")
    void updatePwd(String newPwd, Integer userId);
}
