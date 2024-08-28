package com.mashibing.servicepassengeruser.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class EncryptDecryptUtil {

    public static SimpleStringPBEConfig config(String password){
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setPoolSize(1);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;

    }

    /**
     * 加密
     * @param password 加密的密码
     * @param value 需要加密的值
     * @return
     */
    public static String encryptPwd(String password, String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config(password));
        String encrypt = encryptor.encrypt(value);
        return encrypt;
    }

    /**
     * 解密
     * @param password
     * @param value
     * @return
     */
    public static String decyptPwd(String password, String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config(password));
        String decrypt = encryptor.decrypt(value);
        return decrypt;
    }

    public static void main(String[] args) {
        String name = "root";
        String salt = "mysalt1";


        String nameEncry = encryptPwd(salt, name);
        System.out.println("加密后的数据："+nameEncry);

        String s = decyptPwd(salt, nameEncry);
        System.out.println("解密后的数据："+s);
    }
}
