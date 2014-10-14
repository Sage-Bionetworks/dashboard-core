package org.sagebionetworks.dashboard.config;

import static org.junit.Assert.assertEquals;

import org.apache.shiro.crypto.CryptoException;
import org.junit.Before;
import org.junit.Test;

public class AesEncryptorTest {

    private AesEncryptor encryptor;

    @Before
    public void before() {
        String key = "S7u5V12WCIXRV5SZbINKywUXEOsiNtevaJQYIaqEnKw=";
        encryptor = new AesEncryptor(key);
    }

    @Test
    public void test() {
        String test1 = "test 1";
        String test2 = "test 2";
        String encrypted1 = encryptor.encrypt(test1);
        String encrypted2 = encryptor.encrypt(test2);
        assertEquals(test1, encryptor.decrypt(encrypted1));
        assertEquals(test2, encryptor.decrypt(encrypted2));
    }

    public void testEncryptEmpty() {
        String encrypted = encryptor.encrypt("");
        assertEquals(encrypted, encryptor.decrypt(encrypted));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testEncryptNull() {
        encryptor.encrypt(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDecryptNull() {
        encryptor.decrypt(null);
    }

    @Test(expected=CryptoException.class)
    public void testDecryptInvalid() {
        encryptor.decrypt("not an encrypted text");
    }
}
