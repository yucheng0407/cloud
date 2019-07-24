package net.ruixin.controller;

import net.ruixin.util.assignClass.ServiceClass;
import net.ruixin.util.assignClass.ServiceOperate;
import net.ruixin.util.assignClass.ServiceOperateType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yucheng on 2019/7/24.
 */
@RestController
@ServiceClass(name = "服务管理")
public class ServiceController implements IServiceController {
    @Override
    @RequestMapping("logonService")
    @ServiceOperate(name = "注册服务", type = ServiceOperateType.Query)
    public Boolean logonService(String ip, String port, String application, String services) {
        return null;
    }

    @Override
    @RequestMapping("getUser")
    @ServiceOperate(name = "查找用户", type = ServiceOperateType.Query)
    public List queryService(String ip, String port, String application) {
        return null;
    }
}
