package karol.wlazlo.ds.read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"karol.wlazlo.commons", "karol.wlazlo.ds.read.controllers"})
@EntityScan(basePackages = {"karol.wlazlo.model"})
@EnableJpaRepositories(basePackages = "karol.wlazlo.commons.repositories")
public class DsReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsReadApplication.class, args);
    }

}
