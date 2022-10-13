package com.infosetgroup.flexevent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.cors();
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/login/**", "/register/**", "/h2-console", "/notification/api/**", "/api/rest/public/**", "/api/default/merchant/**", "/api/rest/v1/paymentService/**", "/api/rest/v1/check/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui/**", "/webjars/**").permitAll()
        ;
        http.authorizeRequests().antMatchers("/api/customer/**").hasAuthority("CUSTOMER");
        http.authorizeRequests().antMatchers("/api/rest/sec/**").hasAuthority("CUSTOMER");
        http.authorizeRequests().antMatchers("/api/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()));
        http.addFilterBefore(new JWTAutorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
