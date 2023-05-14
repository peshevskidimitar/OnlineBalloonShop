package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.data.DataHolder;
import mk.ukim.finki.wp.lab.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository {

    public List<Manufacturer> findAll() {
        return new ArrayList<>(DataHolder.manufacturers);
    }

    public Optional<Manufacturer> findById(Long id) {
        return DataHolder.manufacturers.stream()
                .filter(manufacturer -> manufacturer.getId().equals(id))
                .findFirst();
    }

}
