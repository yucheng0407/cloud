package net.ruixin.controller;

import net.ruixin.domain.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/18.
 */
public interface IUserController {
   User getUser(String account, String password);
   Integer SaveUser(User user);
   List<User> getUserList(Map map);
}
