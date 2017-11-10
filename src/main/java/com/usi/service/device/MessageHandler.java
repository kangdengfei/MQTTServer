package com.usi.service.device;

import com.alibaba.fastjson.JSONObject;
import com.usi.service.logger.LoggerFactory;
import com.usi.service.shell.ShellHandler;
import com.usi.service.utils.DeviceConn;
import com.usi.service.utils.XMLAnalysis;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by KDF on 2017/10/31.
 * 针对数据的具体操作
 */
public class MessageHandler {
    private static Logger logger = LoggerFactory.getLogger();

    public static Object ApiHandler(JSONObject jsonObject) {
        String params = jsonObject.getString("message");
        String type = jsonObject.getString("type");
        String url = DeviceConn.getFullURL(params);
        InputStream response = DeviceConn.getResponse(url);
        Object records = null;
        Map<String, Object> back = new HashMap<String, Object>();
        try {
            records = XMLAnalysis.getRecords(response, type);
        } catch (Exception e) {
            //解析Api数据异常
            logger.info("API数据解析异常");
            return null;
        }
        back.put("message", records);
        return back;
    }

    public static Object ControlHandler(JSONObject jsonObject) {
        JSONObject obj = jsonObject.getJSONObject("message");
        String address = obj.getString("address");
        String status = obj.getString("status");
        String cmd;
        if (status.equals("on")) {
            cmd = "usizbgw turnon " + address;
        } else {
            cmd = "usizbgw turnoff " + address;
        }
        ShellHandler.execShell(cmd);
        try {
            //防止过快查询，状态不对
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String res = null;
        cmd = "/usr/local/sbin/usi_device_status.sh " + address;
        int time = 0;
        while (time <= 3 && (res == null || res.trim().isEmpty())) {
            res = ShellHandler.execShell(cmd);
            time++;
        }
        if ((res == null || res.trim().isEmpty())) {
            //shell执行失败或者超时
            res = "3";
            logger.info("shell:" + cmd + "execute failed!");
        }
        Map map1 = new HashMap();
        map1.put("status", res);
        Map map2 = new HashMap();
        map2.put("message", map1);
        return map2;
    }
}
