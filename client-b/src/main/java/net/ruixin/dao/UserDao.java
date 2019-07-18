package net.ruixin.dao;

import net.ruixin.domain.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/18.
 */
@Repository
public class UserDao {
    public User getUser(String account, String password) {
        return null;
    }

    public Integer SaveUser(User user) {
        return null;
    }

    public List<User> getUserList(Map map) {
        return null;
    }
}
