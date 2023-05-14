package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Balloon;

import java.util.List;
import java.util.Optional;

public interface BalloonService {
    List<Balloon> listAll();
    List<Balloon> searchByNameOrDescription(String text);
    Optional<Balloon> findById(Long id);
    void deleteById(Long id);
    Balloon save(Long id, String name, String description, Long manufacturerId);
    List<Balloon> findAllByNameContainingOrDescriptionContainingOrderByName(String text);
    List<Balloon> findAllByNameContainingOrDescriptionContainingOrderByManufacturer(String text);
    List<Balloon> findAllOrderByName();
    List<Balloon> findAllOrderByManufacturer();
}
