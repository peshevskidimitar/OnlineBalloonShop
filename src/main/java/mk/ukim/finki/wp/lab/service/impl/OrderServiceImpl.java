package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Order;
import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.repository.jpa.OrderRepository;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> findAllByCart(ShoppingCart shoppingCart) {
        return orderRepository.findAllByCart(shoppingCart);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @Override
    public Order placeOrder(String balloonName, String balloonColor) {
        return orderRepository.save(new Order(balloonName, balloonColor));
    }

    @Override
    public Order save(String balloonName, String balloonColor, LocalDateTime dateCreated, ShoppingCart shoppingCart) {
        return orderRepository.save(new Order(balloonName, balloonColor, dateCreated, shoppingCart));
    }

    @Override
    public List<Order> findAllByCartAndDateCreatedBetween(ShoppingCart cart, LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAllByCartAndDateCreatedBetween(cart, from, to);
    }

    @Override
    public List<Order> findAllByCartUser(Long userId) {
        return orderRepository.findAllByCartUser(userRepository.findById(userId).get());
    }

    @Override
    public List<Order> findAllByCartUserAndDateCreatedBetween(Long userId, LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAllByCartUserAndDateCreatedBetween(userRepository.findById(userId).get(), from, to);
    }

    @Override
    public List<Order> findAllByCartOrderByDateCreated(ShoppingCart shoppingCart) {
        return orderRepository.findAllByCartOrderByDateCreated(shoppingCart);
    }

    @Override
    public List<Order> findAllByCartAndDateCreatedBetweenOrderByDateCreated(ShoppingCart cart, LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAllByCartAndDateCreatedBetweenOrderByDateCreated(cart, from, to);
    }

    @Override
    public List<Order> findAllBy() {
        return orderRepository.findAllBy();
    }

    @Override
    public List<Order> findAllByDateCreatedBetween(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAllByDateCreatedBetween(from, to);
    }

}
