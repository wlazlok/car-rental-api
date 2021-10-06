package karol.wlazlo.ds.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@ConfigurationPropertiesScan
@EntityScan(basePackages = {"karol.wlazlo.model"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "karol.wlazlo.commons.clients")
public class DsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsAdminApplication.class, args);
    }

}
