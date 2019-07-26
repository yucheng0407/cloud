package net.ruixin.controller;

import net.ruixin.domain.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/18.
 */
public interface IServiceController {
    /**
     *
     * @param ip
     * @param port
     * @param application 项目名
     * @param name 项目全称呼
     * @param services 服务接口数据
     * @return
     */
   // String logonService(String dm,String ip, String port, String application,String name, String services);

    String logonService(Map map);

    /**

     */
    List queryService(String dm);
    /**
     *

     */
    Boolean clearService(String dm);
}
