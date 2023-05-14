package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Balloon;
import mk.ukim.finki.wp.lab.model.Manufacturer;
import mk.ukim.finki.wp.lab.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.BalloonRepository;
import mk.ukim.finki.wp.lab.service.BalloonService;
import mk.ukim.finki.wp.lab.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalloonServiceImpl implements BalloonService {

    private final BalloonRepository balloonRepository;
    private final ManufacturerService manufacturerService;

    public BalloonServiceImpl(BalloonRepository balloonRepository, ManufacturerService manufacturerService) {
        this.balloonRepository = balloonRepository;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public List<Balloon> listAll() {
        return balloonRepository.findAll();
    }

    @Override
    public List<Balloon> searchByNameOrDescription(String text) {
        return balloonRepository.findAllByNameContainingOrDescriptionContaining(text, text);
    }

    @Override
    public Optional<Balloon> findById(Long id) {
        return balloonRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        balloonRepository.deleteById(id);
    }

    @Override
    public Balloon save(Long id, String name, String description, Long manufacturerId) {
        if (id != null)
            deleteById(id);

        Manufacturer manufacturer = manufacturerService.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        return balloonRepository.save(new Balloon(name, description, manufacturer));
    }

    @Override
    public List<Balloon> findAllByNameContainingOrDescriptionContainingOrderByName(String text) {
        return balloonRepository.findAllByNameContainingOrDescriptionContainingOrderByName(text, text);
    }

    @Override
    public List<Balloon> findAllByNameContainingOrDescriptionContainingOrderByManufacturer(String text) {
        return balloonRepository.findAllByNameContainingOrDescriptionContainingOrderByManufacturerName(text, text);
    }

    @Override
    public List<Balloon> findAllOrderByName() {
        return balloonRepository.findAllByOrderByName();
    }

    @Override
    public List<Balloon> findAllOrderByManufacturer() {
        return balloonRepository.findAllByOrderByManufacturerName();
    }

}
