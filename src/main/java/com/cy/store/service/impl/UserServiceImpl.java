package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;


/**
 * 用户模块业务层的实现
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        String username = user.getUsername();
        // 判断用户名是否被占用
        User result = userMapper.findByUserName(username);
        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // md5盐值加密！
        String oldPassword = user.getPassword();
        // 获取盐值(随机生成一个盐值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 补全数据: 盐值的记录
        user.setSalt(salt);
        // 将密码和盐值作一个整体的加密处理,忽略原有密码的强度提升了数据的安全性
        String md5Password = getMD5Password(oldPassword, salt);
        // 将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

        // 补全数据：is_delete设置为：0
        user.setIsDelete(0);
        // 补全数据：4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date data = new Date();
        user.setCreatedTime(data);
        user.setModifiedTime(data);

        // 执行注册也因为功能的实现(rows=1)
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常！");
        }
    }

    @Override
    public User login(String username, String password) {
        // 根据用户名称来查询用户的数据是否存在，如果不存在则抛出异常
        User result = userMapper.findByUserName(username);
        if (result == null) {
            throw  new UserNotFoundException("用户数据不存在！");
        }
        // 一、检测用户的密码是否匹配
        // 1. 获取数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        // 2. 先获取盐值：上一次注册时所自动生成的盐值！
        String salt = result.getSalt();
        // 3. 将用户的密码按照相同的md5算法的规则进行加密
        String newPassword = getMD5Password(password, salt);
        // 4. 将密码进行比较
        if (!newPassword.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        // 判断is_delete字段的值是否为1，表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 这样创建一个用户可以减少字段的传输，提升系统的性能！
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        // 将当前的用户数据返回，返回的数据是为了服务其他页面来做数据的展示(uid,username,avatar)
        return user;
    }

    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword)
    {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 原始密码与数据库密码比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        // 将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password , username , new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生的未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setGender(result.getGender());
        user.setEmail(result.getEmail());

        return user;
    }

    /**
     * user对象中的数据phone、email、gender，手动注入uid、username封装在user对象中！
     * @param uid 用户的id
     * @param username 用户名称
     * @param user 用户的数据
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常!");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像时产生了未知的异常!");
        }
    }

    /**
     * 定义一个md5算法的加密处理
     * @param password 加密的密码
     * @param salt  盐值
     * @return  加密后的密码
     */
    public String getMD5Password (String password, String salt) {
        for (int i = 0; i < 3; i++) {
            // md5 加密方法的调用
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        // 返回加密之后的密码
        return password;
    }
}
