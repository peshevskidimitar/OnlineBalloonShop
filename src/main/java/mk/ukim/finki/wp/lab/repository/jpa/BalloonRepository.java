package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Balloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalloonRepository extends JpaRepository<Balloon, Long> {
    List<Balloon> findAllByOrderByName();
    List<Balloon> findAllByOrderByManufacturerName();

    List<Balloon> findAllByNameContainingOrDescriptionContaining(String name, String description);
    List<Balloon> findAllByNameContainingOrDescriptionContainingOrderByName(String name, String description);
    List<Balloon> findAllByNameContainingOrDescriptionContainingOrderByManufacturerName(String name, String description);

}
