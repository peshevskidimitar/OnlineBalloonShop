package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.ShoppingCart;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidRequiredArgumentsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.lab.service.AuthenticationService;
import mk.ukim.finki.wp.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public LoginController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try {
            user = authenticationService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            ShoppingCart shoppingCart = userService.createShoppingCart(user);
            request.getSession().setAttribute("shoppingCart", shoppingCart);


            return "redirect:/listBalloons";
        } catch (InvalidUserCredentialsException | InvalidRequiredArgumentsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());

            return "login";
        }
    }

}
