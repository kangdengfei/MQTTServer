package com.usi.service.logger;


import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by KDF on 2017/10/17.
 *
 */
public class CustomLogHandler extends Formatter {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    @Override
    public String format(LogRecord record) {
        long millis = record.getMillis();
        String sourceClassName = record.getSourceClassName();
        return simpleDateFormat.format(millis) +" "+sourceClassName+ "\n" + record.getLevel() + ":  msg: " + record.getMessage() + "\n";
    }
}
