package com.infosetgroup.flexevent.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosetgroup.flexevent.dto.LoginRequestData;
import com.infosetgroup.flexevent.entity.AppAccount;
import com.infosetgroup.flexevent.repository.AppAccountRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private AppAccountRepository appAccountRepository;

    LoginRequestData appAccount = null;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        this.appAccountRepository = context.getBean(AppAccountRepository.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            appAccount = new ObjectMapper().readValue(request.getInputStream(), LoginRequestData.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appAccount.getUsername(), appAccount.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain
            chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(a ->{
            roles.add(a.getAuthority());
        });

        boolean success = isAuthorize(roles);
        if (success) {
            AppAccount appAc = this.appAccountRepository.findByUsername(user.getUsername());
            System.out.println("User code : " + appAc.getCode());
            System.out.println("User username : " + appAc.getUsername());

            if (appAccount != null) {
                if (appAc != null) {

                }else {
                    System.out.println("appAc is null");
                }
            }else {
                System.out.println("appAccount is null");
            }

            String username = getName(roles, appAc);

            if(roles.contains("MERCHANT"))
                roles.add("CUSTOMER");

            Date expireAt = null;
            try {
                expireAt =new SimpleDateFormat("dd/MM/yyyy").parse(SecurityParams.EXPIRATION_DATE);
            } catch (ParseException e) {
                System.out.println("Parsing date expiration error");
                e.printStackTrace();
            }

            System.out.println(expireAt);

            String jwt = JWT.create()
                    .withIssuer(request.getRequestURI())
                    .withSubject(user.getUsername())
                    .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(SecurityParams.SECRET));
            response.addHeader(SecurityParams.JWT_HEADER_NAME, jwt);
            response.addHeader("customer", appAc.getCode());
            response.addHeader("username", username);
            response.addHeader("name", appAc.getInscription().getLastname() + " " + appAc.getInscription().getFirstname());
            response.addHeader("roles", roles.get(0));

        }else {
            response.addHeader(SecurityParams.JWT_HEADER_NAME, "");
            response.addHeader("customer", "");
            response.addHeader("roles", "");
            response.setStatus(404);
        }
    }

    private boolean isAuthorize(List<String> roles) {
        for (String rl:roles) {
            if (rl.equalsIgnoreCase("CUSTOMER"))
                return true;
            else if (rl.equalsIgnoreCase("MERCHANT"))
                return true;
            else if (rl.equalsIgnoreCase("MERCHANT"))
                return true;
            else if (rl.equalsIgnoreCase("ADMIN"))
                return true;
        }
        return false;
    }

    private String getName(List<String> roles, AppAccount account) {
        for (String rl:roles) {
            if (rl.equalsIgnoreCase("CUSTOMER")) {
                return account.getUsername();
            }
        }
        return "";
    }

}
