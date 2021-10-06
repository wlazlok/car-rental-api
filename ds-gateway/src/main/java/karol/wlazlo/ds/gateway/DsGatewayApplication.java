package karol.wlazlo.ds.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsGatewayApplication.class, args);
    }

}
