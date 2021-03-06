package com.usi.service.iot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.iot.client.*;
import com.usi.service.device.MessageHandler;
import com.usi.service.logger.LoggerFactory;
import com.usi.service.thread.ListRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by KDF on 2017/10/31
 * 程序接受到消息的处理流程
 */
public class HandleTopic extends AWSIotTopic {
    private static Logger logger = LoggerFactory.getLogger();
    private AWSIotMqttClient client;
    public static String deviceNum = "test";
    private static ExecutorService service = Executors.newFixedThreadPool(10);

    public HandleTopic(String topic, AWSIotQos qos, AWSIotMqttClient client) {
        super(topic, qos);
        this.client = client;
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        //单线程
        String payload = message.getStringPayload();
        JSONObject jsonObject;
        String messageId;
        String handleType;
        try {
            jsonObject = JSONObject.parseObject(payload);
            messageId = jsonObject.getString("messageId");
            handleType = jsonObject.getString("handleType");
        } catch (Exception e) {
            //解析消息出现问题
            logger.info("解析MQTT消息错误");
            return;
        }
        String returnTopic = deviceNum + "/usi/response/" + messageId;
        if (handleType.equals("api")) {
            //调用api请求处理
            Object back = MessageHandler.ApiHandler(jsonObject);
            if (back == null) {
                return;
            }
            try {
                client.publish(returnTopic, AWSIotQos.QOS1, JSON.toJSONString(back));
            } catch (Exception e) {
                //消息返回失败
                logger.info("MQTT消息发送失败");
            }
        } else if (handleType.equals("control")) {
            Object back = MessageHandler.ControlHandler(jsonObject);
            if (back == null) {
                return;
            }
            try {
                client.publish(returnTopic, AWSIotQos.QOS1, JSON.toJSONString(back));
            } catch (Exception e) {
                //消息返回失败
                logger.info("MQTT消息发送失败");
            }
        } else if (handleType.equals("list")) {
            //调用list处理模块
            //耗时较多采用多线程处理
            service.execute(new ListRunnable(client, returnTopic));
        } else {
            //没有找到对应的处理类型
            logger.info("没有找到对应的处理类型");
        }
    }
}
