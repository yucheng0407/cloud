package net.ruixin.dao;

import net.ruixin.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * Created by yucheng on 2019/7/18.
 */

public interface IUserDao extends JpaRepository<User, Integer> {
    @Query("FROM User where loginName=:loginName and passWord=:passWord")
    User getUser(@Param("loginName") String loginName, @Param("passWord") String passWord);
}
