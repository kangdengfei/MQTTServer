package com.usi.service.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by KDF on 2017/10/31.
 * 工具类
 */
public class Utils {
    //判断网络是否可以连接
    public static boolean connect() {
        try {
            InetAddress address1 = InetAddress.getByName("a26tqzs9avsxze.iot.us-east-1.amazonaws.com");
            String hostAddress = address1.getHostAddress();
            if (hostAddress == null) {
                return false;
            }
            InetAddress address2 = InetAddress.getByName("34.203.223.192");
            InetAddress address3 = InetAddress.getByName("8.8.8.8");
            return address3.isReachable(3000) || address2.isReachable(5000);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
