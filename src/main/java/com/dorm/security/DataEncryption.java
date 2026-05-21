package com.dorm.security;

/**
 * 敏感数据加密接口（身份证、联系方式等）。
 * 实现可采用 AES 或与 KMS 集成，密钥不写死在代码中。
 */
public interface DataEncryption {

    String encrypt(String plaintext);
    String decrypt(String ciphertext);
}
