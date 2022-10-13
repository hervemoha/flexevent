package com.infosetgroup.flexevent.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAutorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization, name, username, customer, all, waits, errors, paids, step, phone, ticketing");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
        //if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            //filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else if (httpServletRequest.getRequestURI().equals("/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }else {
            String jwt = httpServletRequest.getHeader(SecurityParams.JWT_HEADER_NAME);
            System.out.println(jwt);
            if (jwt ==null || !jwt.startsWith(SecurityParams.TOKEN_PREFIX)){
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(jwt.substring(SecurityParams.TOKEN_PREFIX.length(), jwt.length()));
            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
            System.out.println(roles);
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            roles.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r));
            });

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,null, authorities);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
