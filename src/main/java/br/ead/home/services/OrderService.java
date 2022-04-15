package br.ead.home.services;

import br.ead.home.models.Order;
import br.ead.home.repositories.OrderRepository;
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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order saveOrUpdate(Order order) {
        log.info("Saving a new order. {}", order);
        return orderRepository.saveOrUpdate(order);
    }

    public Optional<Order> findById(String orderId){
        return orderRepository.findById(orderId);
    }
}
