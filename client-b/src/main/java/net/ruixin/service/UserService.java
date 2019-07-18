package net.ruixin.service;

import net.ruixin.dao.UserDao;
import net.ruixin.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/18.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDAO;

    public User getUser(String account, String password) {
        return userDAO.getUser(account, password);
    }

    public Integer SaveUser(User user) {
        return userDAO.SaveUser(user);
    }

    public List<User> getUserList(Map map) {
        return userDAO.getUserList(map);
    }
}
