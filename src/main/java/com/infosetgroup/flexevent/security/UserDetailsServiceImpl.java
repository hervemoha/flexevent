package com.infosetgroup.flexevent.security;

import com.infosetgroup.flexevent.entity.AppAccount;
import com.infosetgroup.flexevent.repository.AppAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppAccount appAccount = this.appAccountRepository.findByUsername(username);
        if (appAccount == null) throw new UsernameNotFoundException("Invalid user");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appAccount.getRoles().forEach(r -> {
            System.out.println(r.getName());
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        });
        return new User(appAccount.getUsername(), appAccount.getPassword(), authorities);
    }
}
