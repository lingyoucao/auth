package com.demo.token.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    public static Map<String,Object> volidateToken(String jwt) {
        Map<String,Object> result=new HashMap<>(10);
        try {
            Resource resource = new ClassPathResource("public.cert");
            String original_public_key = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            String realmPublicKey= replaceAll(original_public_key);
            PublicKey publicKey = decodePublicKey(pemToDer(realmPublicKey));
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(jwt).getBody();

            result = new ObjectMapper().convertValue(claims,Map.class);
        } catch (Exception ex) {
            result.put("exception",ex.getClass().getName());
            result.put("message",ex.getMessage().split(":")[0]);
        }
        return result;
    }

    private static byte[] pemToDer(String pem){
        return Base64.getDecoder().decode(replaceAll(pem));
    }

    private static String replaceAll(String pem) {
        String stripped = pem.replaceAll("\r\n", "");
        stripped = stripped.replaceAll("\n", "");
        return stripped.trim();
    }


    private static PublicKey decodePublicKey(byte[] der) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Security.addProvider(new BouncyCastleProvider());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(der);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}
