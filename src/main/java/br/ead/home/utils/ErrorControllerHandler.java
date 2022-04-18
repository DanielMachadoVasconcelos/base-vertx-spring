package br.ead.home.utils;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.ext.web.RoutingContext;

import java.util.Map;
import java.util.NoSuchElementException;

public class ErrorControllerHandler {


    public static void handle(Throwable error, RoutingContext context) {

        if (error instanceof NoSuchElementException) {
            context.response()
                    .putHeader("content-type", "application/json")
                    .setStatusCode(404)
                    .end(new JsonObject(Map.of("code", "not_found",
                            "description", error.getMessage()))
                            .encodePrettily());
        } else {
            context.response()
                    .putHeader("content-type", "application/json")
                    .setStatusCode(500)
                    .end(new JsonObject(Map.of("code", "unknown", "description", error.getMessage()))
                            .encodePrettily());
        }
    }
}
