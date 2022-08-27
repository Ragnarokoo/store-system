package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口！
 */
public interface UserMapper {

    /**
     * 插入用户的数据
     *
     * @param user 用户的数据
     * @return 受影响的行数(增 、 删 、 改 ， 都有受影响的行数作为返回值 ， 可以根据返回值来判断是否执行成功 ！)
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     *
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户的数据，如果没有找到则返回null值
     */
    User findByUserName(String username);

    /**
     * 根据用户的uid来修改用户密码
     *
     * @param uid          用户的id
     * @param password     用户输入的新密码
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改数据的时间
     * @return 返回值为受影响的行数
     */
    Integer updatePasswordByUid(@Param("uid") Integer uid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户的id查询用户的数据
     *
     * @param uid 用户的id
     * @return 如果找到返回对象，反之返回null值
     */
    User findByUid(@Param("uid") Integer uid);

    /**
     * 更新用户的数据
     * @param user 用户的数据
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @param("SQL语句中#{}占位符的变量名") 解决：SQL语句的占位符与映射文件的方法参数不一致！
     * 修改用户的头像
     * @param uid 用户的id
     * @param avatar 用户的头像
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);

}
