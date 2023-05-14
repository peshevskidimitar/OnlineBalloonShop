package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.BalloonService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "balloon-list-servlet", urlPatterns = "") // urlPatterns = "" - maps the servlet to app root
public class BalloonListServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final BalloonService balloonService;

    public BalloonListServlet(SpringTemplateEngine springTemplateEngine, BalloonService balloonService) {
        this.springTemplateEngine = springTemplateEngine;
        this.balloonService = balloonService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, getServletContext());
        String searchText = req.getParameter("searchText");
        context.setVariable("balloons", (searchText == null || searchText.isEmpty()) ?
                balloonService.listAll() : balloonService.searchByNameOrDescription(searchText));
        resp.setContentType("text/html;charset=utf-8");
        springTemplateEngine.process("listBalloons.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String balloonColor = req.getParameter("balloonColor");
        req.getSession().setAttribute("balloonColor", balloonColor);
        resp.sendRedirect("/selectBalloon");
    }
}
