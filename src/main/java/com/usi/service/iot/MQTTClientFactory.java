package com.usi.service.iot;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.UUID;

/**
 * Created by KDF on 2017/10/31.
 * mqttclient 工厂类
 */
public class MQTTClientFactory {
    public static AWSIotMqttClient getClient() throws IOException, GeneralSecurityException, AWSIotException {
        InputStream keystore = MQTTClientFactory.class.getClassLoader().getResourceAsStream("my.keystore");
        String clientEndpoint = "a26tqzs9avsxze.iot.us-east-1.amazonaws.com";
        String clientId = UUID.randomUUID().toString();
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        String keyStorePassword = "123456";
        String keyPassword = "123456";
        keyStore.load(keystore, keyStorePassword.toCharArray());
        AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, keyStore, keyPassword);
        client.setMaxConnectionRetries(2);
        client.setConnectionTimeout(5000);
        client.connect();
        return client;
    }
}
