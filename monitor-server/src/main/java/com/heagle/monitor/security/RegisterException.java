package com.heagle.monitor.security;

import org.springframework.security.core.AuthenticationException;

/**
 * author: hui.zhang
 */
public class RegisterException extends AuthenticationException {
    private static final long serialVersionUID = 6712905311762600248L;

    public RegisterException(String msg) {
        super(msg);
    }
}
