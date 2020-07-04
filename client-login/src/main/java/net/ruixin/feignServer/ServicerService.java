package net.ruixin.feignServer;

import net.ruixin.dao.IServiceDao;
import net.ruixin.dao.TestDao;
import net.ruixin.domain.entity.Application;
import net.ruixin.domain.entity.SubService;
import net.ruixin.util.tools.BeanMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by yucheng on 2019/7/18.
 */
@Service
public class ServicerService {
    @Autowired
    IServiceDao serviceDao;
    @Autowired
    TestDao testDao;
    @Transactional
    public Boolean logonService(Map logonMap) {
        try {
            List<Map<String, Object>> services= (List<Map<String,Object>>) logonMap.get("services");
            Application application = BeanMapUtil.mapToBean(logonMap, Application.class);;
            Date date = new Date();
            List<net.ruixin.domain.entity.Service> serviceList = new ArrayList<>();
            application.setCjsj(date);
            for (Map map : services) {
                List<SubService> subServiceList = new ArrayList<>();
                net.ruixin.domain.entity.Service service = BeanMapUtil.mapToBean(map, net.ruixin.domain.entity.Service.class);
                service.setDm(application.getDm() + "/" + service.getDm());
                for (Map subMap : (List<Map>) map.get("subServices")) {
                    SubService subService = BeanMapUtil.mapToBean(subMap, SubService.class);
                    subService.setDm(service.getDm() + "/" + subService.getDm());
                    subService.setSsdm(service.getDm());
                    subService.setCjsj(date);
                    subServiceList.add(subService);
                }
//                Md5Util.EncoderByMd5("11");
                service.setSsdm(application.getDm());
                service.setCjsj(date);
                service.setSubServices(subServiceList);
                serviceList.add(service);
            }
            application.setServices(serviceList);
            serviceDao.save(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    @Transactional
    public Boolean clearService(String dm) {
        serviceDao.deleteById(dm);
        return true;
    }

    public void refreshService(String dm) {
        Optional<Application> opt=serviceDao.findById(dm);
        Application application=opt.get();

    }

    public Map testSql(String sql) {
        return  testDao.testSql(sql);
    }
}
