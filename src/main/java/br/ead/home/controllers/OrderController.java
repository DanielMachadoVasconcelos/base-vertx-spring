package br.ead.home.controllers;

import br.ead.home.models.Order;
import br.ead.home.services.OrderService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Log4j2
@Component
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public void findAll(RoutingContext context) {
        log.info("Finding all the orders!");

        orderService.findAll()
                .map(orders -> orders.stream().map(JsonObject::mapFrom).toList())
                .map(JsonArray::new)
                .map(JsonArray::encodePrettily)
                .subscribe(orders -> context.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end(orders),
                error -> context.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(500)
                        .end(new JsonObject(Map.of("code", "unknown","description", error.getMessage()))
                                .encodePrettily()));

    }

    public void saveOrUpdate(RoutingContext context) {
        log.info("Saving a new order.");
        JsonObject body = context.getBodyAsJson();
        var order = orderService.saveOrUpdate(Order.from(body));
        context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(JsonObject.mapFrom(order).encodePrettily());

    }
}
