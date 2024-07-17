package com.week4.ProductMIS.interceptors;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Example basic authentication logic (replace with your actual authentication mechanism)
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            logger.warn("Unauthorized access. Basic authentication header is missing or invalid.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        // Extract credentials from the header
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);
        String[] parts = credentials.split(":", 2);
        String username = parts[0];
        String password = parts[1];

        // Example: Validate username and password (replace with your authentication logic)
        if (!isValidUser(username, password)) {
            logger.warn("Unauthorized access. Invalid credentials provided.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        return true; // Allow the request to proceed
    }

    // Example method to validate username and password
    private boolean isValidUser(String username, String password) {
        // Replace with your authentication logic (e.g., check against a database)
        return "admin".equals(username) && "password".equals(password);
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Response Status: {}", response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            logger.error("Exception during request processing: {}", ex.getMessage());
        }
    }
}
