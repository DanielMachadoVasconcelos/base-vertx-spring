package br.ead.home.models;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record Order(String orderId, Long amount) {

    public static Order from(JsonObject json) {
        return new Order(json.getString("order_id"), json.getLong("amount"));
    }
}
