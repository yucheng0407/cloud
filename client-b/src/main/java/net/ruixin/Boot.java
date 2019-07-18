package net.ruixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * @author 客户端
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@EnableScheduling
@EnableEurekaClient
public class Boot {
    public static void main(String[] args){
        SpringApplication.run(Boot.class, args);
    }
    @Value("${server.port}")
    String port;

    @RequestMapping("/login")
    public String home(@RequestParam(value = "name", defaultValue = "client-b") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
