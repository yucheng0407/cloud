package net.ruixin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by yucheng on 2019/10/17.
 */
@Repository
public class TestDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map testSql(String sql) {
        return  jdbcTemplate.queryForMap(sql);
    }
}
