package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidRequiredArgumentsException;
import mk.ukim.finki.wp.lab.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.lab.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ShoppingCart createShoppingCart(User user) {
        return shoppingCartRepository.save(new ShoppingCart(user, LocalDateTime.now()));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth, Role role) {
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                repeatPassword == null || repeatPassword.isEmpty() ||
                name == null || name.isEmpty() ||
                surname == null || surname.isEmpty() ||
                dateOfBirth == null)
            throw new InvalidRequiredArgumentsException();

        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();

        User user = new User(username, name, surname, passwordEncoder.encode(password), dateOfBirth, role);

        if (userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
