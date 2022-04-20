package br.ead.home.controllers;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.ext.web.RoutingContext;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ErrorAdviceHandler {

    public static void onError(Throwable throwable, RoutingContext context) {

        if(throwable instanceof IllegalArgumentException error) {
            respondBadRequest(error, context);
            return ;
        }

        context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(500)
                .end(new JsonObject(Map.of("code", "unknown", "description", throwable.getMessage()))
                        .encodePrettily());
    }

    private static void respondBadRequest(IllegalArgumentException error, RoutingContext context) {
        context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                .end(new JsonObject(Map.of("code", "bad_request", "description", error.getMessage()))
                        .encodePrettily());
    }
}
