package net.ruixin.udp;

import com.alibaba.druid.proxy.jdbc.ClobProxy;
import net.ruixin.service.IDeviceRealtimeService;
import net.ruixin.util.tools.EHCacheTool;
import oracle.sql.CLOB;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/2.
 */
@Component
public class SendCache implements InitializingBean {
    @Autowired
    IDeviceRealtimeService iDeviceRealtimeService;
    @Autowired
    private EHCacheTool ehCacheTool;

    @Override
    public void afterPropertiesSet() {
        List<Map<String, Object>> list = iDeviceRealtimeService.getServers();
        List<Map<String, Object>> inlist = iDeviceRealtimeService.getInformations();
        ehCacheTool.setCacheValue("service", list);
        ehCacheTool.setCacheValue("Information", inlist);
    }

    public List getServer(String id) {
        List<Map> list = (List) ehCacheTool.getCacheValue("service");
        List returnList = new ArrayList();
        for(Map map:list){
            String deviceauth=oracleClobToString((Clob)map.get("DEVICEAUTH"));
           if(deviceauth.contains(id)){
               returnList.add(map);
           }
        }
        return returnList;
    }
    public Map getInformation(String vehicleNo) {
        List<Map> list = (List) ehCacheTool.getCacheValue("Information");
        for(Map map:list){
            String device_code=map.get("DEVICE_CODE").toString();
            if(device_code.contains(vehicleNo)){
                return map;
            }
        }
        return null;
    }
    /**
     * Oracle的Clob转成String
     * @param clob
     * @return
     */
    public String oracleClobToString(Clob clob){
        try {
            return (clob == null ? null : clob.getSubString(1, (int)clob.length()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
