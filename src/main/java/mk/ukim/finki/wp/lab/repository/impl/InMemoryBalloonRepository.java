package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.data.DataHolder;
import mk.ukim.finki.wp.lab.model.Balloon;
import mk.ukim.finki.wp.lab.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryBalloonRepository {

    public List<Balloon> findAllBalloons() {
        return new ArrayList<>(DataHolder.balloons);
    }

    public List<Balloon> findAllByNameOrDescription(String text) {
        return DataHolder.balloons.stream()
                .filter(balloon -> balloon.getName().contains(text) || balloon.getDescription().contains(text))
                .collect(Collectors.toList());
    }

    public Optional<Balloon> findById(Long id) {
        return DataHolder.balloons.stream()
                .filter(balloon -> balloon.getId().equals(id))
                .findFirst();
    }

    public boolean deleteById(Long id) {
        return DataHolder.balloons.removeIf(balloon -> balloon.getId().equals(id));
    }

    public Balloon save(Long id, String name, String description, Manufacturer manufacturer) {
        Balloon balloon = new Balloon(name, description, manufacturer);
        DataHolder.balloons.add(balloon);

        return balloon;
    }

}
