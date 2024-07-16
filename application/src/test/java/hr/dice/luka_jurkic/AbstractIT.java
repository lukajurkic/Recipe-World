package hr.dice.luka_jurkic;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.test.junit5.FlywayTestExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(
        basePackages = {"hr.dice.luka_jurkic"},
        excludeFilters = @ComponentScan.Filter(TestConfiguration.class)
)
@ExtendWith({FlywayTestExtension.class, MockitoExtension.class})
@ActiveProfiles(profiles = "test")
public abstract class AbstractIT {

    @LocalServerPort
    private Integer port;
    private RestAssuredConfig config;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        RestAssured.baseURI = "http://localhost:" + port;
        log.info("Running test for {}#{}", testInfo.getTestClass().get().getSimpleName(), testInfo.getTestMethod().get().getName());
        Thread.currentThread().setName(Thread.currentThread().getId() + "-" + this.getClass().getSimpleName());
    }

    @PostConstruct
    void setupRestAssured() {
        RestAssured.requestSpecification = (new RequestSpecBuilder()).setPort(this.port).setAccept(ContentType.JSON).setContentType(ContentType.JSON).build();
        config = RestAssured.config.encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(StandardCharsets.UTF_8));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    void baseTearDown() {
    }

}
