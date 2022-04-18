package br.ead.home.clients;

import br.ead.home.models.Order;
import io.vertx.core.json.Json;
import io.vertx.rxjava3.ext.web.client.HttpResponse;
import io.vertx.rxjava3.ext.web.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderClient {

    @Autowired
    private WebClient webClient;

    public Order placeOrder(String orderId, Long amount) {
        return webClient.post("/api/v1/orders")
                .putHeader("content-type", "application/json")
                .rxSendJson(new Order(orderId, amount, null))
                .map(HttpResponse::bodyAsJsonObject)
                .map(json -> Json.decodeValue(Json.encode(json), Order.class))
                .blockingGet();
    }

    public Order getOrder(String orderId) {
        return webClient.get(String.format("/api/v1/orders/%s", orderId))
                .putHeader("content-type", "application/json")
                .rxSend()
                .map(HttpResponse::bodyAsJsonObject)
                .map(json -> Json.decodeValue(Json.encode(json), Order.class))
                .blockingGet();
    }
}
