package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest // 表示当前是一个测试类，不会随着项目一块打包！
@RunWith(SpringRunner.class) // 表示启动这个单元测试类(没有的话单元测试类是不能运行的)，需要传递一个参数，必须是SpringRunner的实例类型！
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * * 单元测试方法：
     * 1.必须被@test注解修饰
     * 2.返回的类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("tom");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void testQuery() {
        System.out.println(userMapper.findByUserName("tom"));
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(7,"123","测试者1",new Date());
    }

    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(7));
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(8);
        user.setEmail("test001@qq.com");
        user.setPhone("15512345678");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(8,"/update/avatar.png","test",new Date());
    }
}

