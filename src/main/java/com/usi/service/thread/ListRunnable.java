package com.usi.service.thread;

import com.alibaba.fastjson.JSON;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.usi.service.cache.DeviceCache;
import com.usi.service.logger.LoggerFactory;

import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by KDF on 2017/10/31.
 * 线程实现
 */
public class ListRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger();
    private AWSIotMqttClient client;
    private String topic;

    public ListRunnable(AWSIotMqttClient client, String topic) {
        this.client = client;
        this.topic = topic;
    }

    public void run() {
        Map<String, Object> deviceList = DeviceCache.getDeviceList();
        try {
            client.publish(topic, AWSIotQos.QOS1, JSON.toJSONString(deviceList));
        } catch (AWSIotException e) {
            //消息返回失败
            logger.info("MQTT消息发送失败");
        }
    }
}
