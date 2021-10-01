package com.senlainc.security;

import com.senlainc.entity.Account;

public interface AuthenticationAccess {
    Account getAuthenticatedAccount();
}
