package br.ead.home.repositories;

import br.ead.home.models.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Repository
public class OrderRepository {

    private static final Map<String, Order> database = new HashMap<>(10);

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(database.get(orderId));
    }

    public List<Order> findAll() {
        return database.values().stream().toList();
    }

    public Order saveOrUpdate(Order order) {
        return Optional.ofNullable(database.put(order.orderId(), order))
                .orElse(order);
    }
}
