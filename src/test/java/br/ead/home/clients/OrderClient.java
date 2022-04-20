package br.ead.home.clients;

import br.ead.home.models.Order;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.json.Json;
import io.vertx.rxjava3.core.buffer.Buffer;
import io.vertx.rxjava3.ext.web.client.HttpResponse;
import io.vertx.rxjava3.ext.web.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderClient {

    @Autowired
    private WebClient placeOrderRequest;

    public Order placeOrder(String orderId, Long amount) {
        return placeOrderRequest(orderId, amount)
                .map(HttpResponse::bodyAsJsonObject)
                .map(json -> Json.decodeValue(Json.encode(json), Order.class))
                .blockingGet();
    }

    public Order getOrder(String orderId) {
        return getOrderRequest(orderId)
                .map(HttpResponse::bodyAsJsonObject)
                .map(json -> Json.decodeValue(Json.encode(json), Order.class))
                .blockingGet();
    }

    public Single<HttpResponse<Buffer>> placeOrderRequest(String orderId, Long amount) {
        return placeOrderRequest.post("/api/v1/orders")
                .putHeader("content-type", "application/json")
                .rxSendJson(new Order(orderId, amount, null));
    }

    public Single<HttpResponse<Buffer>> getOrderRequest(String orderId) {
        return placeOrderRequest.get(String.format("/api/v1/orders/%s", orderId))
                .putHeader("content-type", "application/json")
                .rxSend();
    }
}
