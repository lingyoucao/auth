package com.demo.token.util;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class PublicKeyUtil {

    public static void main(String[] args) {


        try {

            FileInputStream is = new FileInputStream("e://jwt.jks");
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 密码
            String password = "fuzhou1234567890";
            char[] passwd = password.toCharArray();
            keystore.load(is, passwd);
            String alias = "jwt";
            Key key = keystore.getKey(alias, passwd);
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);
                // Get public key
                PublicKey publicKey = cert.getPublicKey();

                String publicKeyString = Base64.encodeBase64String(publicKey
                        .getEncoded());
                System.out.println(publicKeyString);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
