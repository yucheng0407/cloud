package net.ruixin.controller;

import net.ruixin.domain.entity.User;
import net.ruixin.service.UserService;
import net.ruixin.util.assignClass.FindClass;
import net.ruixin.util.assignClass.ServiceClass;
import net.ruixin.util.assignClass.ServiceOperate;
import net.ruixin.util.assignClass.ServiceOperateType;
import net.ruixin.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/23.
 */
@RestController
@ServiceClass(id = "YHFW", name = "用户服务")
public class UserController implements IUserController {
    @Autowired
    UserService userService;
    @Autowired
    IServiceController serviceController;

    @Override
    @RequestMapping("getUser")
    @ServiceOperate(id = "CZYH", name = "查找用户", type = ServiceOperateType.Query)
    public User getUser(String account, String password) {
//        FindClass.getMe(classes,RequestMapping.class);
        return userService.getUser(account, password);
    }

    @RequestMapping("getRole")
    public List getRole() {
        List list = FindClass.getServiceClass("net.ruixin.controller");
//        FindClass.getMe(classes,RequestMapping.class);
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String service = JacksonUtil.toJson(list);
            serviceController.logonService("FWYHXT",ip, "8765", "client-login", "服务与用户管理系统", service);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return list;
        }
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
