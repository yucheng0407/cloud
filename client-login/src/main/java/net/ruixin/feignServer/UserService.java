package net.ruixin.feignServer;

import net.ruixin.dao.IUserDao;
import net.ruixin.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yucheng on 2019/7/18.
 */
@Service
public class UserService {
    @Autowired
    IUserDao userDAO;

    public User getUser(String account, String password) {
        return userDAO.getUser(account, password);
    }

    public User SaveUser(User user) {
        userDAO.save(user);
        return user;
    }
}
