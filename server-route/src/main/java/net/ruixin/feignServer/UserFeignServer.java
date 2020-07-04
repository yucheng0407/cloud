package net.ruixin.feignServer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient("client-login")
public interface  UserFeignServer {
    @RequestMapping("getUser")
    @ResponseBody
     Map getUser(@RequestParam("account")String account, @RequestParam("password")String password);
}
