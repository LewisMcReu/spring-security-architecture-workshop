package wf.garnier.spring.security.testing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class ForbiddenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if ("true".equalsIgnoreCase(request.getHeader("x-forbidden"))) {
            // These two lines are required to have emojis in your responses.
            // - Character encoding needs to be set before you write to the response.
            // - Content-Type is for browser-based interactions
            // YES EMOJIS ARE VERY IMPORTANT, THANK YOU VERY MUCH
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("text/plain;charset=utf-8");

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("⛔⛔⛔⛔ this is forbidden");
            response.getWriter().close(); // optional

            // Absolutely make sure you don't call the rest of the filter chain!!
            return;
        }

        filterChain.doFilter(request, response);
    }

}
