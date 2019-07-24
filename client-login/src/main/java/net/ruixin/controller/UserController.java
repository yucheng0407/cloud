package net.ruixin.controller;

import net.ruixin.domain.entity.User;
import net.ruixin.service.UserService;
import net.ruixin.util.assignClass.FindClass;
import net.ruixin.util.assignClass.ServiceClass;
import net.ruixin.util.assignClass.ServiceOperate;
import net.ruixin.util.assignClass.ServiceOperateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/23.
 */
@RestController
@ServiceClass(name = "用户服务")
public class UserController implements IUserController {
    @Autowired
    UserService userService;

    @Override
    @RequestMapping("getUser")
    @ServiceOperate(name = "查找用户", type = ServiceOperateType.Query)
    public User getUser(String account, String password) {
//        FindClass.getMe(classes,RequestMapping.class);
        return userService.getUser(account, password);
    }

    @RequestMapping("getRole")
    @ServiceOperate(name = "查找权限", type = ServiceOperateType.Query)
    public List getRole() {
        List list = FindClass.getServiceClass("net.ruixin.controller");
//        FindClass.getMe(classes,RequestMapping.class);
        return list;
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
