package net.ruixin;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.config.server.EnableConfigServer;
/**
 * @author 服务消费者
 */
@SpringBootApplication(exclude =DataSourceAutoConfiguration.class)
@EnableEurekaClient
//@EnableDiscoveryClient
@EnableZuulProxy //路由代理
@EnableAdminServer //admin管理
@EnableFeignClients //远程调用
public class Boot {
    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }
}
