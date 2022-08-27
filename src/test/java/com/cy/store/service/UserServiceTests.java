package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest // 表示当前是一个测试类，不会随着项目一块打包！
@RunWith(SpringRunner.class) // 表示启动这个单元测试类(没有的话单元测试类是不能运行的)，需要传递一个参数，必须是SpringRunner的实例类型！
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    /**
     * * 单元测试方法：
     * 1.必须被@test注解修饰
     * 2.返回的类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void register() {
        try {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("123456");
            userService.register(user);
            System.out.println("ok,插入成功！");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("test01", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(8,"测试001","123","321");
    }

    @Test
    public void getByUid() {
        System.err.println(userService.getByUid(11));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setEmail("admin@qq.com");
        user.setPhone("17896345896");
        user.setGender(0);
        userService.changeInfo(11,"管理员",user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(11,"/upload/images.png","管理员");

    }
}

