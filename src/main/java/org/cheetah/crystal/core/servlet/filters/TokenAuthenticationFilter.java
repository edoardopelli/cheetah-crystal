package org.cheetah.crystal.core.servlet.filters;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.cheetah.crystal.redis.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationFilter implements Filter {

    @Autowired
    private RedisService redisService;

    private static final Set<String> EXCLUDE_URLS = new HashSet<>();

    static {
        EXCLUDE_URLS.add("/register");
        EXCLUDE_URLS.add("/confirm-pin");
        EXCLUDE_URLS.add("/swagger-ui.html");
        EXCLUDE_URLS.add("/v3/api-docs");
        EXCLUDE_URLS.add("/swagger-ui/");
        EXCLUDE_URLS.add("/v3/api-docs/");
        EXCLUDE_URLS.add("/swagger-resources/");
        EXCLUDE_URLS.add("/webjars/");
        EXCLUDE_URLS.add("/auth/login");
        EXCLUDE_URLS.add("/auth/login/otp");
        EXCLUDE_URLS.add("/auth/2fa/login");
        EXCLUDE_URLS.add("/auth/2fa/otp");
        EXCLUDE_URLS.add("/actuator");
        EXCLUDE_URLS.add("/validate/key");
        EXCLUDE_URLS.add("/register/generate");
        EXCLUDE_URLS.add("/error");
        EXCLUDE_URLS.add("/actuator");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (isExcluded(path)) {
            chain.doFilter(request, response);
            return;
        }

        String token = httpRequest.getHeader("X-Crystal-Token");

        if (token != null && !token.isEmpty()) {
            UserDetails user = redisService.getUserByToken(token);
            if (user != null) {
            	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
            	 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                 SecurityContextHolder.getContext().setAuthentication(authentication);
                // Imposta le informazioni dell'utente come necessarie, ad esempio impostandole su un contesto di sicurezza
                httpRequest.setAttribute("user", user);
            } else {
                // Token non valido o scaduto
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            // Token mancante
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Missing token\"}");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No clean-up needed
    }

    private boolean isExcluded(String path) {
        for (String exclude : EXCLUDE_URLS) {
            if (path.equals(exclude) || path.startsWith(exclude)) {
            	System.out.println("path: "+path+"\texclude: "+exclude);
                return true;
            }
        }
        return false;
    }
}