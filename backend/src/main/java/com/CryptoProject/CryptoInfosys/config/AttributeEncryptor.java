package com.CryptoProject.CryptoInfosys.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Converter
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";

    private static String encryptionSecret;

    public AttributeEncryptor(@Value("${app.crypto.secret}") String encryptionSecret) {
        AttributeEncryptor.encryptionSecret = encryptionSecret;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null || attribute.isBlank()) {
            return attribute;
        }
        return Base64.getEncoder().encodeToString(process(attribute.getBytes(StandardCharsets.UTF_8), Cipher.ENCRYPT_MODE));
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return dbData;
        }
        return new String(process(Base64.getDecoder().decode(dbData), Cipher.DECRYPT_MODE), StandardCharsets.UTF_8);
    }

    private byte[] process(byte[] input, int cipherMode) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipherMode, new SecretKeySpec(normalizeKey().getBytes(StandardCharsets.UTF_8), ALGORITHM));
            return cipher.doFinal(input);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to encrypt or decrypt exchange credentials", ex);
        }
    }

    private String normalizeKey() {
        String secret = encryptionSecret == null ? "" : encryptionSecret.trim();
        if (secret.length() >= 16) {
            return secret.substring(0, 16);
        }
        return String.format("%-16s", secret).replace(' ', '0');
    }
}
