package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.data.DataHolder;
import mk.ukim.finki.wp.lab.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryOrderRepository {

    public List<Order> findAll() {
        return new ArrayList<>(DataHolder.orders);
    }

    public Order save(String balloonName, String balloonColor) {
        Order order = new Order(balloonName, balloonColor);
        DataHolder.orders.add(order);

        return order;
    }

    public void deleteAll() {
        DataHolder.orders.clear();
    }

}
