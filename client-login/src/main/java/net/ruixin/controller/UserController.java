package net.ruixin.controller;

import net.ruixin.domain.entity.User;
import net.ruixin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
       return userService.getUser(account,password);
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
