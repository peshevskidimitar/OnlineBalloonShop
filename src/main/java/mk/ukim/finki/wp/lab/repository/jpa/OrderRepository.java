package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Order;
import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCart(ShoppingCart shoppingCart);
    List<Order> findAllByCartAndDateCreatedBetween(ShoppingCart cart, LocalDateTime from, LocalDateTime to);
    List<Order> findAllByCartOrderByDateCreated(ShoppingCart shoppingCart);
    List<Order> findAllByCartAndDateCreatedBetweenOrderByDateCreated(ShoppingCart cart, LocalDateTime from, LocalDateTime to);
    void deleteAllByCart(ShoppingCart shoppingCart);
    List<Order> findAllByCartUser(User user);
    List<Order> findAllByCartUserAndDateCreatedBetween(User user, LocalDateTime from, LocalDateTime to);
    List<Order> findAllBy();
    List<Order> findAllByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
}
