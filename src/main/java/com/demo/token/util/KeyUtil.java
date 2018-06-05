package com.demo.token.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

@Component
public class KeyUtil {

    public String getPublicKeyDigest() {
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "newland123".toCharArray())
                                                .getKeyPair("jwt");
        PublicKey publicKey = keyPair.getPublic();
        String PublicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String digest = DigestUtils.md5Hex(PublicKeyString);
        return digest.substring(9, 24);
    }
}
