package com.usi.service.cache;

import com.usi.service.entity.Device;
import com.usi.service.logger.LoggerFactory;
import com.usi.service.shell.ShellHandler;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by KDF on 2017/10/31.
 * 数据查询缓存 解决访问过快请求
 */
public class DeviceCache {
    private static Logger logger = LoggerFactory.getLogger();
    private static Map<String, List<Device>> cache = new ConcurrentHashMap<String, List<Device>>();
    private static Timer timer = new Timer();
    private final static Map<String, String> map = new HashMap<String, String>();

    static {
        cache.put("first", new ArrayList<Device>());
        cache.put("second", new ArrayList<Device>());
        map.put("0x00124b001110d05e", "Fan");
        map.put("0x00124b001111f605", "Alarm");
        map.put("0x00124b001111f7d7", "Light");
    }

    public static Map<String, Object> getDeviceList() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("messageType", "list");
        List<Device> list = cache.get("first");
        if (list.isEmpty()) {
            //加锁，进行数据填充
            synchronized (DeviceCache.class) {
                list = cache.get("first");
                if (list.isEmpty()) {
                    List<Device> devices = new ArrayList<Device>();
                    //调用数据
                    String[] addrs = getAddrs();
                    for (String addr : addrs) {
                        if (addr == null || addr.isEmpty() || !map.containsKey(addr)) {
                            continue;
                        }
                        devices.add(getDevice(addr.trim()));
                    }
                    result.put("message", devices);
                    cache.put("first", devices);
                    //创建Task 5秒后清空缓存
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            cache.put("second", cache.get("first"));
                            cache.put("first", new ArrayList<Device>());
                        }
                    }, 6000);
                } else {
                    result.put("message", list);
                }
            }
        } else {
            result.put("message", list);
        }
        return result;
    }

    //下发命令获取数据返回zigbee历史的数据
    private static String[] getAddrs() {
        String s = ShellHandler.execShell("/usr/local/sbin/usi_discovery.sh");
        return s.split("\n");
    }

    //根据每个地址下发命令
    private static Device getDevice(String addr) {
        Device device = new Device();
        device.setAddr(addr);
        String type = map.get(addr);
        if (type == null) {
            type = "UNDIFINE";
        }
        device.setType(type);
        String cmd = "/usr/local/sbin/usi_device_status.sh " + addr;
        String res = null;
        int time = 0;
        while (time <= 3 && (res == null || res.trim().isEmpty())) {
            res = ShellHandler.execShell(cmd);
            time++;
        }
        if (res == null || res.trim().isEmpty()) {
            //shell执行失败或者超时
            logger.info("shell:" + cmd + "execute failed!");
            res = "3";
        }
        device.setStatus(res);
        return device;
    }


}
