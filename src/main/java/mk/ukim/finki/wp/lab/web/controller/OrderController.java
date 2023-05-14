package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.service.OrderService;
import mk.ukim.finki.wp.lab.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String getOrdersPage(Model model,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                @SessionAttribute(required = false) ShoppingCart shoppingCart,
                                @RequestParam(required = false) Long userId,
                                HttpServletRequest request) {
//        model.addAttribute("users", userService.findAll());

//        if (userId != null) {
//            if (from != null && to != null)
//                model.addAttribute("orders", orderService.findAllByCartUserAndDateCreatedBetween(userId, from, to));
//            else
//                model.addAttribute("orders", orderService.findAllByCartUser(userId));
//        } else {
//            if (from != null && to != null)
//                model.addAttribute("orders", orderService.findAllByCartAndDateCreatedBetween(shoppingCart, from, to));
//            else
//                model.addAttribute("orders", orderService.findAllByCart(shoppingCart));
//        }

        if (request.getRemoteUser().equals("admin")) {
            if (from != null && to != null)
                model.addAttribute("orders", orderService.findAllByDateCreatedBetween(from, to));
            else
                model.addAttribute("orders", orderService.findAllBy());
        } else {
            if (from != null && to != null)
                model.addAttribute("orders", orderService.findAllByCartAndDateCreatedBetweenOrderByDateCreated(shoppingCart, from, to));
            else
                model.addAttribute("orders", orderService.findAllByCartOrderByDateCreated(shoppingCart));
        }

        return "userOrders";
    }

    @PostMapping("/delivery-info")
    public String deliveryInfo(HttpSession session,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateCreated) {
        session.setAttribute("dateCreated", dateCreated);

        return "redirect:/confirmationInfo";
    }

    @PostMapping("/confirmation-info")
    public String confirmationInfo(@SessionAttribute String balloonColor,
                                   @SessionAttribute String balloonSize,
                                   @SessionAttribute LocalDateTime dateCreated,
                                   @SessionAttribute ShoppingCart shoppingCart) {
        orderService.save(balloonColor, balloonSize, dateCreated, shoppingCart);

        return "redirect:/orders";
    }

}
