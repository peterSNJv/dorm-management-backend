package com.dorm.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES-GCM 敏感数据加密实现。
 * 密钥通过配置注入，生产环境应使用 KMS 或环境变量，禁止提交到仓库。
 */
@Component
public class DataEncryptionImpl implements DataEncryption {

    private static final String ALG = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LEN = 128;
    private static final int GCM_IV_LEN = 12;

    private final byte[] keyBytes;

    public DataEncryptionImpl(@Value("${dorm.security.encryption-key:0123456789abcdef0123456789abcdef}") String key) {
        if (key.length() < 32) throw new IllegalArgumentException("encryption-key must be at least 32 chars");
        this.keyBytes = key.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[GCM_IV_LEN];
            new java.security.SecureRandom().nextBytes(iv);
            Cipher c = Cipher.getInstance(ALG);
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new GCMParameterSpec(GCM_TAG_LEN, iv));
            byte[] enc = c.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            byte[] combined = new byte[iv.length + enc.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(enc, 0, combined, iv.length, enc.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Encrypt failed", e);
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        try {
            byte[] combined = Base64.getDecoder().decode(ciphertext);
            byte[] iv = new byte[GCM_IV_LEN];
            System.arraycopy(combined, 0, iv, 0, GCM_IV_LEN);
            byte[] enc = new byte[combined.length - GCM_IV_LEN];
            System.arraycopy(combined, GCM_IV_LEN, enc, 0, enc.length);
            Cipher c = Cipher.getInstance(ALG);
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new GCMParameterSpec(GCM_TAG_LEN, iv));
            return new String(c.doFinal(enc), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decrypt failed", e);
        }
    }
}
