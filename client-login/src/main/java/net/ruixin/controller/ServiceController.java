package net.ruixin.controller;

import lombok.extern.slf4j.Slf4j;
import net.ruixin.domain.entity.Application;
import net.ruixin.domain.entity.SubService;
import net.ruixin.service.ServicerService;
import net.ruixin.util.assignClass.ServiceClass;
import net.ruixin.util.assignClass.ServiceOperate;
import net.ruixin.util.assignClass.ServiceOperateType;
import net.ruixin.util.json.JacksonUtil;
import net.ruixin.util.tools.BeanMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/24.
 */
@RestController
@ServiceClass(id = "FWGL", name = "服务管理")
public class ServiceController implements IServiceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ServicerService servicerService;

//    @Override
//    @RequestMapping("logonService")
//    @ServiceOperate(id = "ZCFW", name = "注册服务", type = ServiceOperateType.Add)
//    public String logonService(String dm, String ip, String port, String application, String name, String services) {
//     //   servicerService.logonService(dm, ip, port, application, name, JacksonUtil.readValue(services, List.class));
//        return "SUCEESS";
//    }

    @Override
    @RequestMapping("logonService")
    @ServiceOperate(id = "ZCFW", name = "注册服务", type = ServiceOperateType.Add)
    public String logonService(@RequestBody Map map) {
        try {
            servicerService.logonService(map);
            return "SUCEESS";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("注册服务", e);
            return "ERROR";
        }
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
