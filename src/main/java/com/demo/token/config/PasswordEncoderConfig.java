package com.demo.token.config;

import com.demo.token.crypto.BlowfishPasswordEncoder;
import com.demo.token.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Autowired
    KeyUtil keyUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BlowfishPasswordEncoder(keyUtil.getPublicKeyDigest());
    }
}
