package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {
    ShoppingCart createShoppingCart(User user);
    List<User> findAll();

    User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth, Role role);
}
