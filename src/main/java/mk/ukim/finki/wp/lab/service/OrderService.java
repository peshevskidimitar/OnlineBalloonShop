package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Order;
import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<Order> findAllByCart(ShoppingCart shoppingCart);
    void deleteAll();
    Order placeOrder(String balloonName, String balloonColor);
    Order save(String balloonName, String balloonColor, LocalDateTime dateCreated, ShoppingCart shoppingCart);
    List<Order> findAllByCartAndDateCreatedBetween(ShoppingCart shoppingCart, LocalDateTime from, LocalDateTime to);
    List<Order> findAllByCartUser(Long userId);
    List<Order> findAllByCartUserAndDateCreatedBetween(Long userId, LocalDateTime from, LocalDateTime to);
    List<Order> findAllByCartOrderByDateCreated(ShoppingCart shoppingCart);
    List<Order> findAllByCartAndDateCreatedBetweenOrderByDateCreated(ShoppingCart cart, LocalDateTime from, LocalDateTime to);
    List<Order> findAllBy();
    List<Order> findAllByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
}