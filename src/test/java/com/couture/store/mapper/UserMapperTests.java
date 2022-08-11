package com.couture.store.mapper;

import com.couture.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


/**
 * @author Couture
 * @data: 2022/8/5
 * @description:
 */


// @RunWith: 表示启动这个单元测试（单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Autowired(required = true)
    private UserMapper userMapper;
    /**
     * 单元测试方法：可以单独独立运行，不用启动整个项目，可以做单元测试
     * 1.必需被 @Test 注解修饰
     * 2.返回值类型必需是 void
     * 3.方法的返回参数列表不指定任何类型
     * 4.方法的访问修饰符必需是 public
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println("rows" + rows);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void  updatePasswordByUid() {
        userMapper.updatePasswordByUid(5, "123", "admin", new Date());
    }

    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(5));
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(6);
        user.setPhone("17678787888");
        user.setEmail("admin@cy.com");
        user.setGender(1);
        user.setModifiedUser("admin");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(6, "/upload/avatar.png",
                "admin", new Date());
    }
}
