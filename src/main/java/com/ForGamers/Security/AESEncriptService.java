package com.ForGamers.Security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@Getter
public class AESEncriptService {
    private static final String AES = "AES";
    private static final String CIPHER = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    private final SecretKeySpec keySpec;
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public AESEncriptService(@Value("${app.aes.key}") String key) {
        int len = key.getBytes().length;
        if (len != 16 && len != 24 && len != 32) {
            throw new IllegalArgumentException("La clave debe tener 16, 24 o 32 bytes");
        }
        this.keySpec = new SecretKeySpec(key.getBytes(), AES);
    }

    public String encrypt(String string) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        secureRandom.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);

        byte[] encrypted = cipher.doFinal(string.getBytes());
        byte[] encryptedIvAndText = new byte[IV_LENGTH + encrypted.length];

        System.arraycopy(iv, 0, encryptedIvAndText, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, encryptedIvAndText, IV_LENGTH, encrypted.length);

        return Base64.getEncoder().encodeToString(encryptedIvAndText);
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(cipherText);

        byte[] iv = new byte[IV_LENGTH];
        byte[] encrypted = new byte[decoded.length - IV_LENGTH];

        System.arraycopy(decoded, 0, iv, 0, IV_LENGTH);
        System.arraycopy(decoded, IV_LENGTH, encrypted, 0, encrypted.length);

        Cipher cipher = Cipher.getInstance(CIPHER);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
}