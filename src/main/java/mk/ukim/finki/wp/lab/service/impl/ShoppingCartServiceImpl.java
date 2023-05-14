package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.repository.jpa.OrderRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.wp.lab.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, OrderRepository orderRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void emptyShoppingCart(ShoppingCart shoppingCart) {
        orderRepository.deleteAllByCart(shoppingCart);
        shoppingCartRepository.delete(shoppingCart);
    }

}
