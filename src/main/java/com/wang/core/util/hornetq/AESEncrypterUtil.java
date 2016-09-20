package com.wang.core.util.hornetq;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  
import java.security.spec.AlgorithmParameterSpec;  

import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.IvParameterSpec; 

public class AESEncrypterUtil { 
	public static byte[] iv = new byte[] { 13, 56, 85, 25, 41, 17, -40, -24, 87, 18, -27, 74, -52, 123, -47, -25 };  
  
    private static AESEncrypterUtil aes = null;  
  
    public static byte[] key1 = new byte[] { -21, 24, -17, 26, 21, 54, -15, 64, -52, 100, -99, -93, 71, 123, 12, -1, -117, 18, -18, 118, -3, 85, -74, -47, -88, 19, -5, 33, 22, 110, 82, -58 };  
  
    private AESEncrypterUtil() {  
  
    }  
  
    public static synchronized AESEncrypterUtil getInstance() {  
        if (aes == null) {  
            aes = new AESEncrypterUtil();  
        }  
        return aes;  
    }  
  
    public String encrypt(String msg) {  
  
        String str = "";  
        try {  
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//            kgen.init(128, new SecureRandom(key1));  
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key1);
            kgen.init(128, secureRandom); 
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);  
            SecretKey key = kgen.generateKey();  
            Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);  
            str = asHex(ecipher.doFinal(msg.getBytes()));  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (InvalidAlgorithmParameterException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        }  
        return str;  
    }  
  
    public String decrypt(String value) {  
        try {  
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//            kgen.init(128, new SecureRandom(key1));  
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key1);
            kgen.init(128, secureRandom); 
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);  
            SecretKey key = kgen.generateKey();  
            Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);  
            return new String(dcipher.doFinal(asBin(value)));  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (InvalidAlgorithmParameterException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
  
    private String asHex(byte buf[]) {  
        StringBuffer strbuf = new StringBuffer(buf.length * 2);  
        int i;  
  
        for (i = 0; i < buf.length; i++) {  
            if (((int) buf[i] & 0xff) < 0x10)  
                strbuf.append("0");  
  
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));  
        }  
  
        return strbuf.toString();  
    }  
  
    private byte[] asBin(String src) {  
        if (src.length() < 1)  
            return null;  
        byte[] encrypted = new byte[src.length() / 2];  
        for (int i = 0; i < src.length() / 2; i++) {  
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);  
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);  
  
            encrypted[i] = (byte) (high * 16 + low);  
        }  
        return encrypted;  
  
  
    }  
  
//    public static void main(String args[]) {  
//        String str = "{'name':'Roy','age':'23'}";  
//        System.out.println(str);  
//        str = AESEncrypter.getInstance().encrypt(str);  
//        System.out.println(str);  
//        System.out.println(AESEncrypter.getInstance().decrypt(str));  
//    }  
  
}