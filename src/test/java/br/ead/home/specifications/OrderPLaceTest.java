package br.ead.home.specifications;

import br.ead.home.clients.OrderClient;
import br.ead.home.models.Order;
import io.vertx.core.json.Json;
import io.vertx.rxjava3.ext.web.client.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration-test")
class OrderPLaceTest {

    @Autowired
    private OrderClient client;

    @Test
    @DisplayName("Should successfully place a order when request is valid")
    void shouldPLaceOrderWhenRequestIsValid() {

        //given: a new order
        String expectedOrderId = UUID.randomUUID().toString();
        Long expectedAmount = 100L;

        //when: placing the new order
        var response = client.placeOrder(expectedOrderId, expectedAmount);

        //then: the order should be available for querying
        assertOrder(response, expectedOrderId, expectedAmount);

        //and: and the order is available in the database
        var result = client.getOrder(expectedOrderId);
        assertOrder(result, expectedOrderId, expectedAmount);
    }

    private void assertOrder(Order response, String expectedOrderId, Long expectedAmount) {
        assertAll(
            () -> assertNotNull(response),
            () -> assertEquals(expectedOrderId, response.orderId(), "must have the correct order id"),
            () -> assertEquals(expectedAmount, response.amount(), "should have the correct order amount")
        );
    }
}
