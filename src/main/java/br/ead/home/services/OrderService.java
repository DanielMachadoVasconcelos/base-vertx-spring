package br.ead.home.services;

import br.ead.home.models.Order;
import br.ead.home.repositories.OrderRepository;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Single<List<Order>> findAll() {
        return orderRepository.findAll()
                .doOnError(error -> log.error("There is a error while fetching a order.", error))
                .doOnSuccess(orders -> log.info("Listing the list of all orders. size: [{}]", orders.size()));
    }

    public @NonNull Maybe<Order> saveOrUpdate(Order order) {
        if(order == null){
            return Maybe.error(new IllegalArgumentException("An Order is mandatory!"));
        }

        if(order.amount() == null){
            return Maybe.error(new IllegalArgumentException("Order amount is mandatory!"));
        }

        if(order.amount() < 0){
            return Maybe.error(new IllegalArgumentException("Order amount must be grater or equal to zero!"));
        }

        return orderRepository.saveOrUpdate(order)
                .doOnError(error -> log.error("There is a error while saving or update a order. order: [{}]", order, error))
                .doOnSuccess(result -> log.info("Saving a new order. Order: [{}]", result));
    }

    public Maybe<Order> findById(String orderId){
        return orderRepository.findById(orderId)
                .doOnError(error -> log.error("There is a error while fetching a order. orderId: [{}]", orderId, error))
                .doOnSuccess(result -> log.info("Fetching a order. Order: [{}]", result));
    }
}
