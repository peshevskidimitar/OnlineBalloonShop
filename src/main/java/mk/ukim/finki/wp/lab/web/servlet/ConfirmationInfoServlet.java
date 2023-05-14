package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.OrderService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "confirmation-info-servlet", urlPatterns = "/confirmationInfo")
public class ConfirmationInfoServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final OrderService orderService;

    public ConfirmationInfoServlet(SpringTemplateEngine springTemplateEngine, OrderService orderService) {
        this.springTemplateEngine = springTemplateEngine;
        this.orderService = orderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, getServletContext());
        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("clientAgent", req.getHeader("User-Agent"));
        resp.setContentType("text/html;charset=utf-8");
        springTemplateEngine.process("confirmationInfo.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String balloonColor = (String) req.getSession().getAttribute("balloonColor");
        req.getSession().removeAttribute("balloonColor");
        String balloonSize = (String) req.getSession().getAttribute("balloonSize");
        req.getSession().removeAttribute("balloonSize");
        String clientName = (String) req.getSession().getAttribute("clientName");
        req.getSession().removeAttribute("clientName");
        String clientAddress = (String) req.getSession().getAttribute("clientAddress");
        req.getSession().removeAttribute("clientAddress");

        orderService.placeOrder(balloonColor, balloonSize);

        resp.sendRedirect("/orders");
    }

}
