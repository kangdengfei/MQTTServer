package com.usi.service.cache;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by KDF on 2017/10/31.
 */
public class DeviceCacheTest {
    @Test
    public void getDeviceList() throws Exception {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                long l1 = System.currentTimeMillis();
                Map<String, Object> deviceList = DeviceCache.getDeviceList();
                long l2 = System.currentTimeMillis();
                System.out.println(deviceList);
                System.out.println(l2 - l1);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                long l1 = System.currentTimeMillis();
                DeviceCache.getDeviceList();
                long l2 = System.currentTimeMillis();
                System.out.println(l2 - l1);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                long l1 = System.currentTimeMillis();
                DeviceCache.getDeviceList();
                long l2 = System.currentTimeMillis();
                System.out.println(l2 - l1);
            }
        });
        thread1.start();
        Thread.sleep(3000);
        thread2.start();
        Thread.sleep(15000);
        thread2.start();

    }

}