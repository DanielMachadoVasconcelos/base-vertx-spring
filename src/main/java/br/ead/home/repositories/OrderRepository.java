package br.ead.home.repositories;

import br.ead.home.models.Order;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.vertx.rxjava3.sqlclient.RowIterator;
import io.vertx.rxjava3.sqlclient.RowSet;
import io.vertx.rxjava3.sqlclient.SqlClient;
import io.vertx.rxjava3.sqlclient.templates.SqlTemplate;
import io.vertx.rxjava3.sqlclient.templates.TupleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;

@Log4j2
@Repository
@AllArgsConstructor
public class OrderRepository {

    private static final String FIND_BY_ID = "SELECT * FROM broker.order WHERE broker.order.order_id = #{orderId}";
    private static final String SAVE_OR_UPDATE = "INSERT INTO broker.order (order_id, amount) VALUES (#{order_id}, #{amount}) RETURNING order_id, amount";

    private final SqlClient sqlClient;

    public Maybe<Order> findById(String orderId) {
        return SqlTemplate.forQuery(sqlClient, FIND_BY_ID)
                .mapTo(Order.class)
                .rxExecute(Map.of("orderId", orderId))
                .toMaybe()
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        return iterator.next();
                    }
                    throw new NoSuchElementException(String.format("There is no order with order id [%s]", orderId));
                });
    }

    public Single<List<Order>> findAll() {
       return SqlTemplate.forQuery(sqlClient,"SELECT * FROM broker.order")
                .mapTo(Order.class)
                .rxExecute(Map.of())
                .map(RowSet::iterator)
                .map(it -> {
                    var result = new ArrayList<Order>();
                    it.forEachRemaining(result::add);
                    return result;
                });
    }

    public Maybe<Order> saveOrUpdate(Order order) {
        var orderId = Optional.ofNullable(order.orderId())
                .filter(Predicate.not(String::isBlank))
                .orElse(UUID.randomUUID().toString());
        return SqlTemplate.forUpdate(sqlClient, SAVE_OR_UPDATE)
                .mapFrom(TupleMapper.mapper(result -> Map.of("order_id", orderId, "amount", order.amount())))
                .mapTo(Order.class)
                .rxExecute(order)
                .toMaybe()
                .map(RowSet::iterator)
                .map(RowIterator::next);
    }
}
