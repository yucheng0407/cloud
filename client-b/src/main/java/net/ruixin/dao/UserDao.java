package net.ruixin.dao;

import net.ruixin.domain.entity.User;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * Created by yucheng on 2019/7/18.
 */
@Repository
public class UserDao{
    @Autowired
    EsUserDao esUserDao;

    public User getUser(String account, String password) {
        Pageable pageable = new PageRequest(0,10);
        //按标题进行搜索
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("account", "AD");
//        QueryBuilder queryBuilder = QueryBuilders.matchQuery("account", account);
        esUserDao.search(matchQueryBuilder);
        return null;
    }

    public Integer SaveUser(User user) {
        return null;
    }

    public List<User> getUserList(Map map) {

        return null;
    }
}
