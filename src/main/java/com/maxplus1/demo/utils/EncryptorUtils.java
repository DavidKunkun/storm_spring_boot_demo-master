package com.maxplus1.demo.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptorUtils {
    public static final String ALGORITHM = "AES";

    public static byte[] decrypt(String key)  {
        return Base64.decodeBase64(key);
    }

    public static String encrypt(byte[] key)  {
        return Base64.encodeBase64String(key);
    }

    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }

    private static Key toKey(byte[] key) throws Exception {
        //DESKeySpec dks = new DESKeySpec(key);
        //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        //SecretKey secretKey = keyFactory.generateSecret(dks);

        // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

        return secretKey;
    }

    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    public static String initKey() throws Exception {
        return initKey(null);
    }

    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;

        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }

        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);

        SecretKey secretKey = kg.generateKey();

        return encryptBASE64(secretKey.getEncoded());
    }


    public static void main(String[] args) throws Exception {
        String[] vals = new String[]{"test","test@123"};
        for (String source : vals) {
            System.out.println("原文: " + source);
            String encryptData = encrypt(source.getBytes());
            System.out.println("加密后: " + encryptData);
            String decryptData = new String(decrypt(encryptData));
            System.out.println("解密后: " + decryptData);
        }
    }

}
