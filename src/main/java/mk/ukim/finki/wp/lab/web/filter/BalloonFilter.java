package mk.ukim.finki.wp.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter
public class BalloonFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getServletPath();

        if (!path.startsWith("/login") && !path.startsWith("/register") && !path.startsWith("/logout")
                && !path.startsWith("/balloons") && !path.startsWith("/orders")) {
            String balloonColor = (String) req.getSession().getAttribute("balloonColor");
            if (balloonColor == null && !path.equals("")) {
                resp.sendRedirect("/balloons");
                return;
            }

            String balloonSize = (String) req.getSession().getAttribute("balloonSize");
            if (balloonColor != null && balloonSize == null && !path.equals("/selectBalloon")) {
                resp.sendRedirect("/selectBalloon");
                return;
            }

//            String clientName = (String) req.getSession().getAttribute("clientName");
//            String clientAddress = (String) req.getSession().getAttribute("clientAddress");
//            if (balloonColor != null && balloonSize != null &&
//                    ((clientName == null || clientName.isEmpty()) || (clientAddress == null || clientAddress.isEmpty())) &&
//                    !path.equals("/balloonOrder")) {
//                resp.sendRedirect("/balloonOrder");
//                return;
//            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
