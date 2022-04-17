package br.ead.home.services;

import br.ead.home.models.Order;
import br.ead.home.repositories.OrderRepository;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Single<List<Order>> findAll() {
        return orderRepository.findAll();
    }

    public Single<Order> saveOrUpdate(Order order) {
        log.info("Saving a new order. {}", order);
        return orderRepository.saveOrUpdate(order);
    }

    public Maybe<Order> findById(String orderId){
        return orderRepository.findById(orderId);
    }
}
