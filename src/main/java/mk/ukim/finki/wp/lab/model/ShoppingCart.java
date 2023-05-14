package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enumerations.ShoppingCartStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;
    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;
    @OneToMany(mappedBy = "cart")
    private List<Order> orders;

    public ShoppingCart() {
    }

    public ShoppingCart(User user, LocalDateTime dateCreated) {
        this.user = user;
        this.dateCreated = dateCreated;
        orders = new ArrayList<>();
        status = ShoppingCartStatus.CREATED;
    }

}
