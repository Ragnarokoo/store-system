package com.cy.store.service;

import com.cy.store.entity.User;

/**
 * 用户模块业务层接口
 */
public interface IUserService {

    /**
     * 用户注册方法
     *
     * @param user 用户数据对象
     */
    void register(User user);

    /**
     * 用户登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 当前匹配的用户数据，如果没有返回null值！
     */
    User login(String username, String password);

    /**
     * 修改密码业务
     *
     * @param uid         用户id
     * @param username    用户名
     * @param oldPassword 用户当前密码
     * @param newPassword 用户新密码
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    /**
     * 根据用户的id查询用户的数据
     *
     * @param uid 用户的id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据
     *
     * @param uid      用户的id
     * @param username 用户名称
     * @param user     用户的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 更新用户的头像
     *
     * @param uid          用户id
     * @param avatar       用户头像
     * @param username     修改人
     */
    void changeAvatar(Integer uid, String avatar, String username);

}
