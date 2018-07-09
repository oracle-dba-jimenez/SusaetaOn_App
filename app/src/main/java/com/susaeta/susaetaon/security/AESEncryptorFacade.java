/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.nemesys.crypt.AESEncryptor
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.net.util.Base64
 */
package com.susaeta.susaetaon.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.util.Base64;

public class AESEncryptorFacade { 
    public static Clob encryptFileToCLOB(Connection connection, String params, String filePath) throws EncryptorException {
    	
        try {        	
       	
            Clob clob = connection.createClob();
            String string = Base64.encodeBase64String(AESEncryptor.encryptFile(params, filePath));
            clob.setString(1, string);
            return clob;
        }
        catch (SQLException e) {
            throw new EncryptorException(e);
        }
    }

    public static Clob encryptFileToCLOB(String params, String filePath) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:default:connection:");
            return AESEncryptorFacade.encryptFileToCLOB(connection, params, filePath);
        }
        catch (SQLException e) {
            throw new EncryptorException(e);
        }
    }

    public static Clob encryptBLOBToCLOB(Connection connection, String params, Blob blob) {
        try {
            Clob clob = connection.createClob();
            byte[] bytes = IOUtils.toByteArray((InputStream)blob.getBinaryStream());
            String string = Base64.encodeBase64String(AESEncryptor.encrypt((AESEncryptorParams)AESEncryptorParams.createFromJSON(params), bytes));
            clob.setString(1, string);
            return clob;
        }
        catch (SQLException e) {
            throw new EncryptorException(e);
        }
        catch (IOException e) {
            throw new EncryptorException(e);
        }
    }

    public static Clob encryptBLOBToCLOB(String params, Blob blob) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:default:connection:");
            return AESEncryptorFacade.encryptBLOBToCLOB(connection, params, blob);
        }
        catch (SQLException e) {
            throw new EncryptorException(e);
        }
    }

    public static int encryptBLOBToFile(Connection connection, String params, Blob blob, String filePath) {
        FileOutputStream output = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            output = new FileOutputStream(file);
            byte[] bytes = IOUtils.toByteArray((InputStream)blob.getBinaryStream());
            byte[] encrypted = AESEncryptor.encrypt((AESEncryptorParams)AESEncryptorParams.createFromJSON(params), bytes);
            IOUtils.write((byte[])encrypted, (OutputStream)output);
            output.flush();
            int n = (int)blob.length();
            return n;
        }
        catch (SQLException e) {
            throw new EncryptorException(e);
        }
        catch (IOException e) {
            throw new EncryptorException(e);
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (Throwable throwable) {}
            }
        }
    }

    public static int encryptBLOBToFile(String params, Blob blob, String filePath) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:default:connection:");
            return AESEncryptorFacade.encryptBLOBToFile(connection, params, blob, filePath);
        }
        catch (SQLException e) {
            throw new EncryptorException(e);
        }
    }
}