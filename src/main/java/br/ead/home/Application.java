package br.ead.home;

import br.ead.home.verticles.HttpServerVerticle;
import io.vertx.rxjava3.core.Vertx;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    @Autowired
    private HttpServerVerticle httpServerVerticle;

    @Autowired
    private Vertx vertx;

    @Autowired
    private Flyway flyway;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        flyway.migrate();
        vertx.deployVerticle(httpServerVerticle);
    }
}
