package com.couture.store.service;

import com.couture.store.entity.User;
import com.couture.store.mapper.UserMapper;
import com.couture.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Couture
 * @data: 2022/8/5
 * @description:
 */


// @RunWith: 表示启动这个单元测试（单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired(required = true)
    private IUserService userService;
    /**
     * 单元测试方法：可以单独独立运行，不用启动整个项目，可以做单元测试
     * 1.必需被 @Test 注解修饰
     * 2.返回值类型必需是 void
     * 3.方法的返回参数列表不指定任何类型
     * 4.方法的访问修饰符必需是 public
     */
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("test02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            // 获取类的对象，获取类的名称
            System.out.println(e.getClass().getSimpleName());

            // 获取异常的具体描述信息
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
        userService.changePassword(6,"admin", "123", "321");
    }

    @Test
    public void getByUid() {
        System.err.println(userService.getByUid(6));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("1781763363");
        user.setEmail("yuan@qq.com");
        user.setGender(0);
        userService.changeInfo(6,"admin", user);
    }
}
