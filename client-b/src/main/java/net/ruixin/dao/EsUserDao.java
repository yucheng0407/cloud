package net.ruixin.dao;

import net.ruixin.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by yucheng on 2019/7/22.
 */
public interface EsUserDao extends ElasticsearchDao<User, Integer> {
    Page<User> findByAccountLikeOrNameLike(String account, String name, Pageable pageable);

    Page<User> findByNameLike(String name, Pageable pageable);

    User findByAccountAndPassword(String account, String password);
}
