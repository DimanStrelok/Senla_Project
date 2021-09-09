package com.senlainc.security;

import com.senlainc.entity.Account;
import com.senlainc.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final AccountRepository repository;

    public AuthService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new AccountPrincipal(account);
    }
}
