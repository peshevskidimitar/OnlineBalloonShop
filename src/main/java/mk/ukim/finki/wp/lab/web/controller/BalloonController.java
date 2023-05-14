package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Balloon;
import mk.ukim.finki.wp.lab.model.Manufacturer;
import mk.ukim.finki.wp.lab.model.exceptions.BalloonNotFoundException;
import mk.ukim.finki.wp.lab.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wp.lab.service.BalloonService;
import mk.ukim.finki.wp.lab.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/balloons")
public class BalloonController {

    private final BalloonService balloonService;
    private final ManufacturerService manufacturerService;

    public BalloonController(BalloonService balloonService, ManufacturerService manufacturerService) {
        this.balloonService = balloonService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getBalloonsPage(@RequestParam(required = false) String error, Model model,
                                  @RequestParam(required = false) String searchText,
                                  @RequestParam(required = false) String sortBy) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        if (searchText != null && !searchText.isEmpty()) {
            if (sortBy != null && !sortBy.isEmpty()) {
                if (sortBy.equals("name"))
                    model.addAttribute("balloons",
                            balloonService.findAllByNameContainingOrDescriptionContainingOrderByName(searchText));
                else if (sortBy.equals("manufacturer"))
                    model.addAttribute("balloons",
                            balloonService.findAllByNameContainingOrDescriptionContainingOrderByManufacturer(searchText));
            } else
                model.addAttribute("balloons", balloonService.searchByNameOrDescription(searchText));
        } else {
            if (sortBy != null && !sortBy.isEmpty()) {
                if (sortBy.equals("name"))
                    model.addAttribute("balloons",
                            balloonService.findAllOrderByName());
                else if (sortBy.equals("manufacturer"))
                    model.addAttribute("balloons",
                            balloonService.findAllOrderByManufacturer());
            } else
                model.addAttribute("balloons", balloonService.listAll());
        }

        return "listBalloons";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddBalloonPage(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());

        return "add-balloon";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditBalloonPage(@PathVariable Long id, Model model) {
        Optional<Balloon> optionalBalloon = balloonService.findById(id);
        if (optionalBalloon.isEmpty())
            return "redirect:/balloons?error=" + new BalloonNotFoundException(id).getMessage();

        model.addAttribute("balloon", optionalBalloon.get());
        model.addAttribute("manufacturers", manufacturerService.findAll());

        return "add-balloon";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveBalloon(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Long manufacturerId) {
        try {
            balloonService.save(id, name, description, manufacturerId);
        } catch (BalloonNotFoundException | ManufacturerNotFoundException exception) {
            return "redirect:/balloons?error=" + exception.getMessage();
        }

        return "redirect:/balloons";
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBalloon(@PathVariable Long id) {
        balloonService.deleteById(id);

        return "redirect:/balloons";
    }

}
