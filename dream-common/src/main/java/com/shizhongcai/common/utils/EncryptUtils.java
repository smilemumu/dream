package com.shizhongcai.common.utils;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class EncryptUtils
{
    private static final Random random = new Random();

    public static String getAESRandomKey()
    {
        long longValue = random.nextLong();
        return String.format("%016x", new Object[] { Long.valueOf(longValue) });
    }

    public static Map<String, Object> generateRSAKeyPairs()
            throws NoSuchAlgorithmException
    {
        Map<String, Object> keyPairMap = new HashMap();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyPairMap.put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
        keyPairMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
        return keyPairMap;
    }

    public static byte[] getAesKey(String pwd)
            throws Exception
    {
        if (pwd.length() >= 16)
        {
            String bit128Str = pwd.substring(0, 16);
            return bit128Str.getBytes("UTF-8");
        }
        throw new Exception("AES KEY length < 16");
    }

    public static PublicKey getPublicKeyFromString(String base64String)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte[] bt = Base64.decodeBase64(base64String);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bt);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKeyFromString(String base64String)
            throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        byte[] bt = Base64.decodeBase64(base64String);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bt);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }

    public static boolean verifySignByPublicKey(String inputStr, String publicKey, String base64SignStr)
            throws Exception
    {
        return verifyByPublicKey(inputStr.getBytes(), getPublicKeyFromString(publicKey), Base64.decodeBase64(base64SignStr));
    }

    public static boolean verifyByPublicKey(byte[] data, PublicKey publicKey, byte[] signature)
            throws Exception
    {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        boolean ret = sig.verify(signature);
        return ret;
    }

    public static String signByPrivateKey(String inputStr, String privateKey)
            throws Exception
    {
        return Base64.encodeBase64String(signByPrivateKey(inputStr.getBytes(), getPrivateKeyFromString(privateKey)));
    }

    public static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey)
            throws Exception
    {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        byte[] ret = sig.sign();
        return ret;
    }

    public static String encryptByRSA(String inputStr, String publicKey)
            throws Exception
    {
        PublicKey key = getPublicKeyFromString(publicKey);
        return Base64.encodeBase64String(encryptByRSA(inputStr.getBytes(), key));
    }

    public static byte[] encryptByRSA(byte[] input, Key key)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, key);
        byte[] output = cipher.doFinal(input);
        return output;
    }

    public static String decryptByRSA(String inputStr, String privateKey)
            throws Exception
    {
        PrivateKey key = getPrivateKeyFromString(privateKey);
        return new String(decryptByRSA(Base64.decodeBase64(inputStr), key));
    }

    public static byte[] decryptByRSA(byte[] input, Key key)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, key);
        byte[] output = cipher.doFinal(input);
        return output;
    }

    public static String encryptByAES(String inputStr, String password)
            throws Exception
    {
        byte[] byteData = inputStr.getBytes();
        byte[] bytePassword = password.getBytes();
        return Base64.encodeBase64String(encryptByAES(byteData, bytePassword));
    }

    public static byte[] encryptByAES(byte[] data, byte[] pwd)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(1, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }

    public static String decryptByAES(String inputStr, String password)
            throws Exception
    {
        byte[] byteData = Base64.decodeBase64(inputStr);
        byte[] bytePassword = password.getBytes();
        return new String(decryptByAES(byteData, bytePassword));
    }

    public static byte[] decryptByAES(byte[] data, byte[] pwd)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(2, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }

    public static byte[] decryptByAesPKCS5(byte[] data, byte[] pwd)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(2, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }

    public static String createLinkString(Map<String, String> params)
    {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuffer linkStr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String)keys.get(i);
            String value = (String)params.get(key);
            if (value != null) {
                if (i == keys.size() - 1) {
                    linkStr.append(key).append("=").append(value);
                } else {
                    linkStr.append(key).append("=").append(value).append("&");
                }
            }
        }
        return linkStr.toString();
    }

    public static String MD5(String source)
            throws NoSuchAlgorithmException
    {
        StringBuffer sb = new StringBuffer();
        String part = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = source.getBytes();
        byte[] md5 = md.digest(bytes);
        for (int i = 0; i < md5.length; i++)
        {
            part = Integer.toHexString(md5[i] & 0xFF);
            if (part.length() == 1) {
                part = "0" + part;
            }
            sb.append(part);
        }
        return sb.toString();
    }

    public static boolean verifySignByPublicKey4MicroSoft(String signData, String partnerPublicKey, String sign)
            throws Exception
    {
        return verifySignByPublicKey(signData, partnerPublicKey, sign);
    }

    public static String decryptByRSA4MicroSoft(String inputStr, String privateKey)
            throws Exception
    {
        PrivateKey key = getPrivateKeyFromString(privateKey);
        return new String(decryptByRSA4MicroSoft(Base64.decodeBase64(inputStr), key));
    }

    private static byte[] decryptByRSA4MicroSoft(byte[] input, PrivateKey key)
            throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding");
        cipher.init(2, key);
        byte[] output = cipher.doFinal(input);
        return output;
    }

    public static String encryptByRSA4MicroSoft(String inputStr, String publicKey)
            throws Exception
    {
        PublicKey key = getPublicKeyFromString(publicKey);
        return Base64.encodeBase64String(encryptByRSA4MicroSoft(inputStr.getBytes(), key));
    }

    private static byte[] encryptByRSA4MicroSoft(byte[] input, PublicKey key)
            throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding");
        cipher.init(1, key);
        byte[] output = cipher.doFinal(input);
        return output;
    }
}
