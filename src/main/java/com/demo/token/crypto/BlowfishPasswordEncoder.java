package com.demo.token.crypto;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BlowfishPasswordEncoder implements PasswordEncoder {
    private String key;

    public BlowfishPasswordEncoder(String key) {
        this.key = key;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String encodedPassword = BlowfishEncryptor.encrypt(rawPassword.toString(), key);
        return encodedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword.equals(BlowfishEncryptor.encrypt(rawPassword.toString(), key))) {
            return true;
        }
        throw new BadCredentialsException("您的授权码非法");
    }
}
