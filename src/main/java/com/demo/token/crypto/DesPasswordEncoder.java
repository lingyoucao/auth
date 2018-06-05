package com.demo.token.crypto;

import com.demo.token.util.DesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DesPasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(DesPasswordEncoder.class);
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            String encodedText = DesUtil.encrypt(rawPassword.toString());
            return encodedText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword.equals(encode(rawPassword))) {
            return true;
        }
        logger.error("您的授权码非法");
        throw new BadCredentialsException("您的授权码非法");
    }
}
