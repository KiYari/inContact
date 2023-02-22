package com.gruppa.incontact.accounts.config.filters;

import com.gruppa.incontact.accounts.security.JwtTokenUtil;
import com.gruppa.incontact.accounts.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter  extends OncePerRequestFilter {
    protected final UserService userService;
    protected final JwtTokenUtil jwtTokenUtils;

    public JwtRequestFilter(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")
                && !requestTokenHeader.equals("Bearer ")
                && !requestTokenHeader.equals("Bearer")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                String username = jwtTokenUtils.getUsernameFromToken(jwtToken);
                if(!username.equals("") && null== SecurityContextHolder.getContext().getAuthentication()) {
                    UserDetails user = userService.loadUserByUsername(username);
                    if (jwtTokenUtils.validateToken(jwtToken, user)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext()
                                .setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.error("Unable to fetch JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token is expired");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("JWT token does not  begin with Bearer String");
        }

        filterChain.doFilter(request, response);
    }
}
