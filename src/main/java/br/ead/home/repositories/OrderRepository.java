package br.ead.home.repositories;

import br.ead.home.models.Order;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.vertx.rxjava3.sqlclient.RowSet;
import io.vertx.rxjava3.sqlclient.SqlClient;
import io.vertx.rxjava3.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;

@Log4j2
@Repository
@AllArgsConstructor
public class OrderRepository {

    private final SqlClient sqlClient;

    public Maybe<Order> findById(String orderId) {
        return sqlClient.preparedQuery("SELECT * FROM broker.order WHERE broker.order.order_id = #{order_id}")
                .rxExecute(Tuple.of("order_id", orderId))
                .map(RowSet::iterator)
                .map(it -> {
                    if(it.hasNext()){
                        var row = it.next();
                        return new Order(row.getString("order_id"),
                                row.getLong("amount"));
                    }
                    return null;
                })
                .toMaybe();
    }

    public Single<List<Order>> findAll() {
        return sqlClient.query("SELECT * FROM broker.order")
                .rxExecute()
                .map(RowSet::iterator)
                .map(it -> {
                    var result = new ArrayList<Order>();
                    while(it.hasNext()){
                        var row = it.next();
                        result.add(new Order(row.getString("order_id"),
                                row.getLong("amount")));
                    }
                    return result;
                });
    }

    public Single<Order> saveOrUpdate(Order order) {
        return Single.just(order);
    }
}
