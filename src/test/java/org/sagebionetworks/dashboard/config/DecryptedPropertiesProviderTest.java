package org.sagebionetworks.dashboard.config;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class DecryptedPropertiesProviderTest {

    @Test
    public void test() {
        final String key = "S7u5V12WCIXRV5SZbINKywUXEOsiNtevaJQYIaqEnKw=";
        final AesEncryptor encryptor = new AesEncryptor(key);
        final String key1 = "key1";
        final String testValue1 = "test value 1";
        final String encrypted1 = DecryptedPropertiesProvider.PREFIX + encryptor.encrypt(testValue1);
        final String key2 = "key2";
        final String testValue2 = "test value 2";
        final String encrypted2 = DecryptedPropertiesProvider.PREFIX + encryptor.encrypt(testValue2);
        final String key3 = "key3";
        final String testValue3 = "test value 3";
        Properties properties = new Properties();
        properties.setProperty(key1, encrypted1);
        properties.setProperty(key2, encrypted2);
        properties.setProperty(key3, testValue3);
        BasicPropertiesProvider provider = new BasicPropertiesProvider(properties);
        DecryptedPropertiesProvider decryptedProvider = new DecryptedPropertiesProvider(key, provider);
        Properties decrypted = decryptedProvider.getProperties();
        assertEquals(testValue1, decrypted.getProperty(key1));
        assertEquals(testValue2, decrypted.getProperty(key2));
        assertEquals(testValue3, decrypted.getProperty(key3));
    }
}
