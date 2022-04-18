package br.ead.home.routes;

import br.ead.home.controllers.OrderController;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Log4j2
@Component
@Order(1)
@AllArgsConstructor
public class OrderRoutes {

    private final OrderController orderController;
    private final Router router;

    @PostConstruct
    public void configureOrderRoutes() throws Exception {

        log.info("Configuring the Order Routes");

        router.get("/api/v1/orders")
                .handler(orderController::findAll);

        router.post("/api/v1/orders")
                .handler(orderController::saveOrUpdate);
    }

}
