package com.susaeta.susaetaon.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.net.util.Base64;

public class AESEncryptorParams implements Serializable {
    private static final long serialVersionUID = 5724753223179407669L;
    private static final String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$";
    public static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEXP);
    public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%*()_+-=,<>./[]?";
    public final byte[] key;
    public final byte[] initVector;
    private final String stringified;

    private static String pad(String string) {
        if (string.length() > 16) {
            return string.substring(0, 17);
        }
        return String.format("%1$16s", string).replace(' ', '*');
    }

    public static byte[] stringToKey(String key) throws UnsupportedEncodingException {
        if (key == null) {
            return null;
        }
        String trimmed = key.trim();
        if (trimmed.length() < 8) {
            throw new IllegalArgumentException("Key length < 8");
        }
        if (!STRONG_PASSWORD_PATTERN.matcher(trimmed).matches()) {
        	throw new IllegalArgumentException("Is not strong");
        }
        String padded = trimmed.length() < 16 ? AESEncryptorParams.pad(trimmed) : trimmed;
        return padded.getBytes("UTF-8");
    }

    public static boolean isValidKey(String key) throws UnsupportedEncodingException {
        if (AESEncryptorParams.stringToKey(key) != null) {
            return true;
        }
        return false;
    }

    public static int isValidKeyBit(String key) throws UnsupportedEncodingException {
        return AESEncryptorParams.stringToKey(key) != null ? 1 : 0;
    }

    public static String stringToInitVector(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random() * (double)ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public AESEncryptorParams(String key) throws EncryptorException {
        try {
            HashMap<String, String> json = new HashMap<String, String>();
            Gson gson = new GsonBuilder().create();
            String initVector = AESEncryptorParams.stringToInitVector(16);
            this.key = AESEncryptorParams.stringToKey(key);
            this.initVector = initVector.getBytes("UTF-8");
            json.put("key", key);
            json.put("initVector", Base64.encodeBase64String((byte[])this.initVector));
            this.stringified = gson.toJson(json);
        }
        catch (UnsupportedEncodingException e) {
            throw new EncryptorException(e);
        }
    }

    private AESEncryptorParams(Map<String, String> json, Gson gson) throws EncryptorException {
        String key = json.get("key");
        byte[] initVector = Base64.decodeBase64((String)json.get("initVector"));
        try {
            this.key = AESEncryptorParams.stringToKey(key);
            this.initVector = initVector;
            this.stringified = gson.toJson(json);
        }
        catch (UnsupportedEncodingException e) {
            throw new EncryptorException(e);
        }
    }

    public String toString() {
        return this.stringified;
    }

    public int hashCode() {
        int result = 301 + (this.stringified == null ? 0 : this.stringified.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        AESEncryptorParams other = (AESEncryptorParams)obj;
        if (this.stringified == null ? other.stringified != null : !this.stringified.equals(other.stringified)) {
            return false;
        }
        return true;
    }

    public static String createAsJSON(String key) {
        return new AESEncryptorParams(key).toString();
    }

    @SuppressWarnings("unchecked")
	public static AESEncryptorParams createFromJSON(String string) {
        Gson gson = new GsonBuilder().create();
        Map<String,String> json = (Map<String,String>)gson.fromJson(string, Map.class); 
        return new AESEncryptorParams(json, gson);
    }
}