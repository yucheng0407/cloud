package net.ruixin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 服务端
 */
@SpringBootApplication(exclude =DataSourceAutoConfiguration.class)
@EnableEurekaServer
public class Boot {
    public static void main(String[] args){
        SpringApplication.run(Boot.class, args);
    }
}
