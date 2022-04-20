package br.ead.home.specifications;

import br.ead.home.clients.OrderClient;
import br.ead.home.models.Order;
import io.netty.handler.codec.http.HttpResponseStatus;
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

    @Test
    @DisplayName("Should not allow to place a order when the amount is not provided")
    void shouldNotAllowToPLaceOrderWhenRequestIsMissingTheAmount() {

        //given: a new order and invalid order
        String expectedOrderId = UUID.randomUUID().toString();
        Long expectedAmount = null;

        //when: placing the invalid order
        var response = client.placeOrderRequest(expectedOrderId, expectedAmount).blockingGet();

        //then: the response should be 404
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.statusCode(), HttpResponseStatus.BAD_REQUEST.code());

        //and: the response body has correct message
        String expectedErrorMessage = "Order amount is mandatory!";
        Assertions.assertEquals(response.bodyAsJsonObject().getString("description"), expectedErrorMessage);

    }

    private void assertOrder(Order response, String expectedOrderId, Long expectedAmount) {
        assertAll(
            () -> assertNotNull(response),
            () -> assertEquals(expectedOrderId, response.orderId(), "must have the correct order id"),
            () -> assertEquals(expectedAmount, response.amount(), "should have the correct order amount")
        );
    }
}
