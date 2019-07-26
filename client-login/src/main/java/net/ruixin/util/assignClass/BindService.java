package net.ruixin.util.assignClass;

import net.ruixin.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yucheng on 2019/7/26.
 */
@Component
public class BindService {
    private RestTemplate restTemplate = new RestTemplate();
    private List activataeServices;
    private List unActivataeServices;
    private String sendSuccess;

    @Scheduled(fixedDelay = 30000)
    public void sendService() {
        try {
            if ("SUCCESS".equals(sendSuccess)) return;
            Map map = new HashMap();
            List list = FindClass.getServiceClass("net.ruixin.controller");
            String ip = InetAddress.getLocalHost().getHostAddress();
            map.put("dm", "FWYHXT");
            map.put("ip", ip);
            map.put("port", "8765");
            map.put("application", "client-login");
            map.put("name", "服务与用户管理系统");
            map.put("services", list);
            String token = restTemplate.getForObject("http://localhost:8760/getToken", String.class);
            sendSuccess = restTemplate.postForObject("http://localhost:8760/client-login/logonService?token=" + token, map, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("receiveServices")
    public void receiveActivateServices(@RequestBody List activataeServices, @RequestBody List unActivataeServices) {
        this.activataeServices = activataeServices;
        this.unActivataeServices = unActivataeServices;
    }

    public List getActivataeServices() {
        return activataeServices;
    }

    public List getUnActivataeServices() {
        return unActivataeServices;
    }
}
