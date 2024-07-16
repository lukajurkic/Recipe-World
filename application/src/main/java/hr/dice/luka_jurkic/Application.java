package hr.dice.luka_jurkic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan("hr.dice.luka_jurkic.persistence.entity")
@EnableJpaRepositories("hr.dice.luka_jurkic.persistence.repository")
@SpringBootApplication()
@EnableFeignClients(basePackages = "hr.dice.luka_jurkic.client")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}