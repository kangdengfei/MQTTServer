package com.usi.service.device;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.usi.service.iot.HandleTopic;
import com.usi.service.iot.MQTTClientFactory;
import com.usi.service.logger.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by KDF on 2017/10/11.
 */
public class Client {
    static Logger logger = LoggerFactory.getLogger();
    private static List<AWSIotMqttClient> clients = new ArrayList<AWSIotMqttClient>();
    public static String deviceNum = "test";

    public static void getStart() throws AWSIotException, GeneralSecurityException, IOException {
        AWSIotMqttClient subclient = MQTTClientFactory.getClient();
        AWSIotMqttClient pubclient = MQTTClientFactory.getClient();
        if (null == subclient || null == pubclient) {
            return;
        }
        clients.add(subclient);
        clients.add(pubclient);
        subclient.setNumOfClientThreads(10);
        AWSIotTopic deviceTopic = new HandleTopic(deviceNum + "/usi/request", AWSIotQos.QOS1, pubclient);
        try {
            subclient.subscribe(deviceTopic);
        } catch (AWSIotException e) {
            logger.info(Client.class.getSimpleName() + " :订阅失败");
        }
    }

    public static void getEnd() {
        for (AWSIotMqttClient client : clients) {
            try {
                client.disconnect();
            } catch (AWSIotException e) {
                logger.info(Client.class.getSimpleName() + " :停止异常");
            }
        }
        clients.clear();
    }
}
