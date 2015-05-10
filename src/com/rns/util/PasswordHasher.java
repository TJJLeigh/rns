package com.rns.util;



import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import java.security.SecureRandom;
import java.util.Random;




public class PasswordHasher {

    public static String md5Hex(String plaintext){
        return DigestUtils.md5Hex(plaintext);
    }
    public static String generateSalt(){
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[10];
        r.nextBytes(saltBytes);
        return Base64.encodeBase64String(saltBytes);
    }
}
