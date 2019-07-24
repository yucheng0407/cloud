package net.ruixin.controller;

import net.ruixin.domain.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/18.
 */
public interface IServiceController {
    Boolean logonService(String ip, String port, String application, String services);

    List queryService(String ip, String port, String application);
}
