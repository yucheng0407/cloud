package net.ruixin.controller;

import net.ruixin.service.ServicerService;
import net.ruixin.util.assignClass.ServiceClass;
import net.ruixin.util.assignClass.ServiceOperate;
import net.ruixin.util.assignClass.ServiceOperateType;
import net.ruixin.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yucheng on 2019/7/24.
 */
@RestController
@ServiceClass(id = "FWGL", name = "服务管理")
public class ServiceController implements IServiceController {
    @Autowired
    ServicerService servicerService;

    @Override
    @RequestMapping("logonService")
    @ServiceOperate(id = "ZCFW", name = "注册服务", type = ServiceOperateType.Add)
    public Boolean logonService(String dm, String ip, String port, String application, String name, String services) {
        servicerService.logonService(dm, ip, port, application, name, JacksonUtil.readValue(services, List.class));
        return true;
    }

    @Override
    @RequestMapping("queryService")
    @ServiceOperate(id = "CZFW", name = "查找服务", type = ServiceOperateType.Query)
    public List queryService(String dm) {
        return null;
    }

    @Override
    @RequestMapping("clearService")
    @ServiceOperate(id = "QKFW", name = "清空服务", type = ServiceOperateType.Delete)
    public Boolean clearService(String dm) {
        return servicerService.clearService(dm);
    }
}
