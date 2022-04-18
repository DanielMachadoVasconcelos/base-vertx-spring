package br.ead.home.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@With
@Builder
public record Order(@JsonProperty("order_id") String orderId,
                    @JsonProperty("amount") Long amount,
                    @JsonProperty("created_at") LocalDateTime createdAt) {

    public static Order from(JsonObject json) {
        return new Order(json.getString("order_id"),
                         json.getLong("amount"),
                         LocalDateTime.from(json.getInstant("created_at")));
    }
}
