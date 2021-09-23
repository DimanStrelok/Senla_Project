package com.senlainc.security;

import com.senlainc.entity.Account;
import com.senlainc.exception.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAccessImpl implements AuthenticationAccess {
    @Override
    public Account getAuthenticatedAccount() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            throw new AppException("getAuthorizedAccount must be called in authenticated context");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AccountPrincipal) {
            AccountPrincipal accountPrincipal = (AccountPrincipal) principal;
            return accountPrincipal.getAccount();
        }
        throw new AppException("principal must be AccountPrincipal");
    }
}
