package net.ruixin.dao;

import net.ruixin.domain.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by yucheng on 2019/7/18.
 */

public interface IServiceDao extends JpaRepository<Application, String> {
    @Query(" FROM Application where dm=:dm")
    Application getUser(@Param("dm") String loginName);
}
