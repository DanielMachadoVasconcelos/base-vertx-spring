package br.ead.home.specifications;

import br.ead.home.models.Order;
import br.ead.home.services.OrderService;
import br.ead.home.utils.ErrorControllerHandler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;

import static br.ead.home.utils.ErrorControllerHandler.*;

@Log4j2
@Component
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public void findById(RoutingContext context) {
        var orderId = context.pathParam("orderId");
        orderService.findById(orderId)
            .subscribe(order -> context.response()
                            .putHeader("content-type", "application/json")
                            .setStatusCode(200)
                            .end(Json.encode(order)),
                    error -> handle(error, context));

    }

    public void findAll(RoutingContext context) {
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
                                .end(new JsonObject(Map.of("code", "unknown", "description", error.getMessage()))
                                        .encodePrettily()));

    }

    public void saveOrUpdate(RoutingContext context) {
        JsonObject body = context.getBodyAsJson();
        orderService.saveOrUpdate(Order.from(body))
                .subscribe(order -> context.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(201)
                                .end(JsonObject.mapFrom(order).encodePrettily()),
                        error -> context.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(500)
                                .end(new JsonObject(Map.of("code", "unknown", "description", error.getMessage()))
                                        .encodePrettily()));

    }
}
