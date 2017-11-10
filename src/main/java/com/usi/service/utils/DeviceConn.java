package com.usi.service.utils;


import com.usi.service.logger.LoggerFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 * 连接Device的api调用
 */
public class DeviceConn {
    private static Logger logger = LoggerFactory.getLogger();

    public static String getFullURL(String parmas) {
        StringBuilder sb = new StringBuilder("http://127.0.0.1:8080/rest/");
        sb.append("p_username=admin&p_password=admin&");
        sb.append(parmas);
        return sb.toString();
    }

    //获取输出流
    public static InputStream getResponse(String url) {
        InputStream inputStream = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realURL = new URL(url);
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输入流
            conn.getOutputStream();
            inputStream = conn.getInputStream();
        } catch (Exception e) {
            logger.info("访问api报错");
        }
        return inputStream;
    }
}
