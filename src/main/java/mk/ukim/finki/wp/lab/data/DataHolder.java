package mk.ukim.finki.wp.lab.data;

import mk.ukim.finki.wp.lab.model.Balloon;
import mk.ukim.finki.wp.lab.model.Manufacturer;
import mk.ukim.finki.wp.lab.model.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataHolder {

    public static List<Balloon> balloons = new ArrayList<>();
    public static List<Manufacturer> manufacturers = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<String> colors =
                Arrays.asList("Green", "Red", "Blue", "Yellow", "Pink", "Orange", "Purple", "Navy", "Aqua", "Lime");

        manufacturers.add(new Manufacturer("Creative Balloons Manufacturing", "USA", "Carmel, CA"));
        manufacturers.add(new Manufacturer("Balloons Everywhere", "USA", "Fresno, CA"));
        manufacturers.add(new Manufacturer("Nikki's Balloons", "USA", "Elton, MD"));
        manufacturers.add(new Manufacturer("American Balloon Factory", "USA", "Shawnee Mission, KS"));
        manufacturers.add(new Manufacturer("E&R Sales", "USA", "Richmond, VA"));

        colors.stream()
                .map(color -> new Balloon(String.format("%s Balloon", color),
                        String.format("This is a %s Balloon.", color),
                        manufacturers.get((int) (Math.random() * manufacturers.size()))))
                .forEach(balloons::add);
    }

}
