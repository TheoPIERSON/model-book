package com.masphoto.masphoto.jwt;

import com.masphoto.masphoto.entities.Jwt;
import com.masphoto.masphoto.service.JwtService;
import com.masphoto.masphoto.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Exclure les endpoints de connexion et d'inscription
        if (requestURI.endsWith("/user/connexion")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        Jwt tokenInDatabase = null;
        String username = null;
        boolean isTokenExpired = true;

        logger.info("Request URI: " + requestURI);
        logger.info("Authorization Header: " + request.getHeader("Authorization"));

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            tokenInDatabase = this.jwtService.tokenByValue(token);
            isTokenExpired = jwtService.isTokenExpired(token);
            username = jwtService.readUsername(token);
        }

        if (!isTokenExpired
                && tokenInDatabase != null
                && tokenInDatabase.getUser().getEmail().equals(username)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }


}
