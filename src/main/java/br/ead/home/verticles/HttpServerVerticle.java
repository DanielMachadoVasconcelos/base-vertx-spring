package br.ead.home.verticles;

import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class HttpServerVerticle extends AbstractVerticle {

    private final Router router;

    @Override
    public void start() {
        log.info("Starting the Http Server");
        vertx.createHttpServer()
                .requestHandler(router::handle)
                .rxListen(8080)
                .subscribe(httpServer -> log.info("Server started successfully on port {}", httpServer.actualPort()),
                        error -> log.error("Http server did not start successfully", error));
    }
}
