package net.ruixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 服务端
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaServer
public class Boot {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYYMM");
        String d=simpleDateFormat.format(new Date());
        SpringApplication.run(Boot.class, args);
    }
}
