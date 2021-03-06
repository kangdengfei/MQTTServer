package com.usi.service.shell;

import com.usi.service.logger.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Created by KDF on 2017/10/31.
 * Shell 执行方法 设置两秒等待
 */
public class ShellHandler {
    private static Logger logger = LoggerFactory.getLogger();

    public static String execShell(String command) {
        StringBuilder sb = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            //解析数据异常
            logger.info("shell 执行异常");
            return null;
        }
        return sb.toString().trim();
    }
}
