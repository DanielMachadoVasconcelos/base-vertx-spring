package br.ead.home.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
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
                .listen(config().getInteger("http.port", 8080), event -> {
                    if(event.succeeded()){
                        log.info("Server started sucessfully on port {}", event.result().actualPort());
                    }else {
                        log.error("Http server did not start successfully", event.cause());
                    }
                });
    }
}
