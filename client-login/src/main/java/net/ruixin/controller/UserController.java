package net.ruixin.controller;

import net.ruixin.domain.entity.User;
import net.ruixin.service.UserService;
import net.ruixin.util.assignClass.FindClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/23.
 */
@RestController
public class UserController implements IUserController {
    @Autowired
    UserService userService;

    @Override
    @RequestMapping("/getUser")
    public User getUser(String account, String password) {
        List<Class<?>> classes=FindClass.getClasses("net.ruixin.controller",RestController.class);
//        FindClass.getMe(classes,RequestMapping.class);
        return userService.getUser(account, password);
    }

    @Override
    public Integer SaveUser(User user) {
        return null;
    }

    @Override
    public List<User> getUserList(Map map) {
        return null;
    }
}
