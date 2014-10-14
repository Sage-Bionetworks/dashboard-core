package org.sagebionetworks.dashboard.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AbstractSymmetricCipherService;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.OperationMode;
import org.apache.shiro.util.ByteSource;

/**
 * 256-bit AES CBC block cipher. All the values, keys are in UTF-8 and
 * then encoded in Base64.
 */
public class AesEncryptor {

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            AesEncryptor encryptor = new AesEncryptor();
            AbstractSymmetricCipherService cipher = encryptor.getCipher();
            String key = Base64.encodeToString(cipher.generateNewKey(256).getEncoded());
            System.out.println("A new 256-bit key in Base64 format : " + key);
            return;
        }

        if (args.length < 2) {
            throw new RuntimeException("Supply no argument for generating a new key; "
                    + "otherwise, must supply at least 2 arguments: "
                    + "<key in Base64 format>, <target>, "
                    + "decrypt|encrypt (optional, when missing, encrypt)");
        }

        final String key = args[0];
        AesEncryptor encryptor = new AesEncryptor(key);
        System.out.println("Key: " + key);

        final String target = args[1];
        System.out.println("Target: " + target);

        if (args.length == 2 || "encrypt".equalsIgnoreCase(args[2])) {
            String encrypted = encryptor.encrypt(target);
            System.out.println("Encrypted: " + encrypted);
            return;
        }
        String decrypted = encryptor.decrypt(target);
        System.out.println("Decrypted: " + decrypted);
    }

    public AesEncryptor(String base64EncodedEncryptionKey) {
        if (base64EncodedEncryptionKey == null ||
                base64EncodedEncryptionKey.isEmpty()) {
            throw new IllegalArgumentException("Encryption key cannot be null or empty.");
        }
        this.encryptionKey = Base64.decode(base64EncodedEncryptionKey);
        cipher = new AesCipherService();
        cipher.setKeySize(256);
        cipher.setMode(OperationMode.CBC);
    }

    public String encrypt(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        return cipher.encrypt(value.getBytes(UTF_8), encryptionKey).toBase64();
    }

    public String decrypt(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        ByteSource byteSource = cipher.decrypt(Base64.decode(value), encryptionKey);
        byte[] bytes = byteSource.getBytes();
        return new String(bytes, UTF_8);
    }

    private AesEncryptor() {
        cipher = new AesCipherService();
        cipher.setKeySize(256);
        cipher.setMode(OperationMode.CBC);
        encryptionKey = null;
    }

    private AbstractSymmetricCipherService getCipher() {
        return cipher;
    }

    private final AesCipherService cipher;
    private final byte[] encryptionKey;
}
