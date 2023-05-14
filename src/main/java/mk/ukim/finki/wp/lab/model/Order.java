package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "balloon_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String balloonColor;
    private String balloonSize;
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime dateCreated;
    @ManyToOne
    private ShoppingCart cart;

    public Order() {
    }

    public Order(String balloonColor, String balloonSize) {
        this.balloonColor = balloonColor;
        this.balloonSize = balloonSize;
    }

    public Order(String balloonColor, String balloonSize, LocalDateTime dateCreated, ShoppingCart shoppingCart) {
        this.balloonColor = balloonColor;
        this.balloonSize = balloonSize;
        this.dateCreated = dateCreated;
        this.cart = shoppingCart;
    }

}
