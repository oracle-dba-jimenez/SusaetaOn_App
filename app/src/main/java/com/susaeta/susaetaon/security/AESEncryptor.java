package com.susaeta.susaetaon.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.net.util.Base64;

public class AESEncryptor {
    public static final String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    public static final String SECRET_KEY_SPEC_NAME = "AES";

    
    public static byte[] encrypt(byte[] key, byte[] initVector, byte[] value) throws EncryptorException {
        try {
        	
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, SECRET_KEY_SPEC_NAME);
            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(1, (Key)skeySpec, iv);
            
            byte[] encrypted = cipher.doFinal(value);            
            return encrypted;      
    		
        }
        catch (IllegalBlockSizeException e) {
            throw new EncryptorException(e); 
        }
        catch (BadPaddingException e) {
            throw new EncryptorException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new EncryptorException(e);
        }
        catch (NoSuchPaddingException e) {
            throw new EncryptorException(e);
        }
        catch (InvalidKeyException e) {
            throw new EncryptorException(e);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new EncryptorException(e);
        }
    }

    public static String encrypt(String key, String initVector, byte[] value) throws EncryptorException {
        try {
            return Base64.encodeBase64String((byte[])AESEncryptor.encrypt(key.getBytes("UTF-8"), initVector.getBytes("UTF-8"), value));
        }
        catch (UnsupportedEncodingException e) {
            throw new EncryptorException(e);
        }
    }

    public static String decrypt(String key, String initVector, byte[] encrypted) throws EncryptorException {
        try {
            byte[] original = AESEncryptor.decrypt(key.getBytes("UTF-8"), initVector.getBytes("UTF-8"), encrypted);
            return new String(Base64.decodeBase64((byte[])original));
        }
        catch (UnsupportedEncodingException e) {
            throw new EncryptorException(e);
        }
    }

    public static byte[] decrypt(byte[] key, byte[] initVector, byte[] encrypted) throws EncryptorException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, SECRET_KEY_SPEC_NAME);
            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(2, (Key)skeySpec, iv);
            byte[] original = cipher.doFinal(encrypted);
            return original;
        }
        catch (IllegalBlockSizeException e) {
            throw new EncryptorException(e);
        }
        catch (BadPaddingException e) {
            throw new EncryptorException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new EncryptorException(e);
        }
        catch (NoSuchPaddingException e) {
            throw new EncryptorException(e);
        }
        catch (InvalidKeyException e) {
            throw new EncryptorException(e);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new EncryptorException(e);
        }
    }

    public static byte[] decrypt(AESEncryptorParams params, byte[] encrypted) throws EncryptorException {
        return AESEncryptor.decrypt(params.key, params.initVector, encrypted);
    }

    public static byte[] decrypt(String params, byte[] encrypted) throws EncryptorException {
        return AESEncryptor.decrypt(AESEncryptorParams.createFromJSON(params), encrypted);
    }

    public static byte[] encrypt(AESEncryptorParams params, byte[] value) throws EncryptorException {
        return AESEncryptor.encrypt(params.key, params.initVector, value);
    }

    public static String decrypt(AESEncryptorParams params, String encrypted) throws EncryptorException {
        try {
            return new String(AESEncryptor.decrypt(params, Base64.decodeBase64((String)encrypted)), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new EncryptorException(e);
        }
    }

    public static String encrypt(AESEncryptorParams params, String value) throws EncryptorException {
        try {
            return Base64.encodeBase64String((byte[])AESEncryptor.encrypt(params, value.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e) {
            throw new EncryptorException(e);
        }
    }

    public static String decrypt(String params, String encrypted) throws EncryptorException {
        return AESEncryptor.decrypt(AESEncryptorParams.createFromJSON(params), encrypted);
    }

    public static String encrypt(String params, String value) throws EncryptorException {
        return AESEncryptor.encrypt(AESEncryptorParams.createFromJSON(params), value);
    }

    
    public static byte[] encryptFile(String params, String filePath) throws EncryptorException {
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            byte[] arrby = AESEncryptor.encrypt(AESEncryptorParams.createFromJSON(params), buffer);
            return arrby;
        }
        catch (IOException e) {
            throw new EncryptorException(e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (Throwable throwable) {}
            }
        }
    }

    public static String encryptFileToBase64(String params, String filePath) throws EncryptorException {
            return Base64.encodeBase64String(encryptFile(params, filePath));
        
    }
    
    public static byte[] encryptFile(String params, File filePath) throws EncryptorException {
        return AESEncryptor.encryptFile(params, filePath.getAbsolutePath());
    }

    public static byte[] decryptFile(String params, String filePath) throws EncryptorException {
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            byte[] arrby = AESEncryptor.decrypt(AESEncryptorParams.createFromJSON(params), buffer);
            return arrby;
        }
        catch (IOException e) {
            throw new EncryptorException(e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (Throwable throwable) {}
            }
        }
    }

    public static byte[] decryptFile(String params, File filePath) throws EncryptorException {
        return AESEncryptor.decryptFile(params, filePath.getAbsolutePath());
    }

    public static String encryptFileToString(String params, String filePath) throws EncryptorException {
        return Base64.encodeBase64String((byte[])AESEncryptor.encryptFile(params, filePath));
    }

    public static String encryptFileToString(String params, File filePath) throws EncryptorException {
        return Base64.encodeBase64String((byte[])AESEncryptor.encryptFile(params, filePath));
    }
}