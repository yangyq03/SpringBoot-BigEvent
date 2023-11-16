package com.example.bigevent.controller;

import com.example.bigevent.Utils.JwtUtil;
import com.example.bigevent.Utils.Md5Util;
import com.example.bigevent.Utils.ThreadLocalUtil;
import com.example.bigevent.pojo.Result;
import com.example.bigevent.pojo.User;
import com.example.bigevent.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated  //使用了Validation进行参数校验
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        //在数据库中查找用户名是否已经存在
        User u = userService.findByUsername(username);
        if (u == null) {
            //没有被占用，需要注册
            userService.register(username, password);
            return Result.success();
        } else {
            //已经被占用
            return Result.error("用户名已经被占用");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUsername(username);
        if (loginUser == null) {
            return Result.error("用户名错误或未注册");
        }
        //已注册
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //密码正确，登录成功
            //生成token令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    //获取用户所有信息
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    //更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        //通过ThreadLocal获取用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //获取原密码
        String password = userService.findByUsername(username).getPassword();
        //判断密码是否正确
        if (!Md5Util.checkPassword(oldPwd, password)) {
            return Result.error("原密码错误");
        }
        //校验两次填写的密码是否正确
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的密码不一致");
        }
        //密码正确，更新密码
        userService.updatePwd(newPwd);
        return Result.success();
    }

}
