package karol.wlazlo.ds.update;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"karol.wlazlo.commons", "karol.wlazlo.ds.update"}, exclude = {ThymeleafAutoConfiguration.class})
@EntityScan(basePackages = {"karol.wlazlo.model"})
@EnableJpaRepositories(basePackages = "karol.wlazlo.commons.repositories")
public class DsUpdateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsUpdateApplication.class, args);
    }

}
