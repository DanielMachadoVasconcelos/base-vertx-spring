package br.ead.home;

import br.ead.home.verticles.HttpServerVerticle;
import io.vertx.core.Vertx;
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        vertx.deployVerticle(httpServerVerticle);
    }
}
