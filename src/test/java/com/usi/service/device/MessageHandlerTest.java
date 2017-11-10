package com.usi.service.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by KDF on 2017/10/31.
 */
public class MessageHandlerTest {

    @Test
    public void apiHandler() throws Exception {
        String jsonString = "\"handleType\": \"api\",\n" +
                "  \"messageId\": \"ff4cdd3111b4442c8be7a9d0c5b93044\",\n" +
                "  \"type\": \"current\",\n" +
                "  \"message\": \"p_operation=get&p_context=users.admin.devices.modbus&p_variable=temp_humidity\"";
        JSONObject object = JSONObject.parseObject(jsonString);
        Object o = MessageHandler.ApiHandler(object);
        System.out.println(o);
    }

    @Test
    public void controlHandler() throws Exception {
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("address", "0x00124b001110db14");
        map2.put("status", "on");
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("message", map2);
        JSONObject object = JSONObject.parseObject(JSON.toJSONString(map1));
        Object o = MessageHandler.ControlHandler(object);
        System.out.println(o);
    }

}