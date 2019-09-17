package com.monitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ResourceUtil {
    private static Logger logger = LoggerFactory.getLogger(ResourceUtil.class);
    private static HashMap<String, MessageFormat> formats  = new HashMap<String, MessageFormat>();

    private static ResourceBundle   rsBundle = ResourceBundle.getBundle("conf/develop/resource");

    public static String getMessage(String msgKey, Object[] param) {

        if (param != null && param.length > 0) {
            synchronized (formats) {
                MessageFormat messageFormat = formats.get(msgKey);
                String value = rsBundle.getString(msgKey);
                if (value == null || value.length() <= 0) {
                    return value;
                }
                if (messageFormat == null) {
                    messageFormat = new MessageFormat(value);
                    formats.put(msgKey, messageFormat);
                }
                return messageFormat.format(param);
            }
        } else {
            return getMessage(msgKey);
        }
    }

    public static String getMessage(String msgKey) {

        return IOS2UTF8(rsBundle.getString(msgKey));
    }

    public static String getMessage(long msgKey) {

        return rsBundle.getString(String.valueOf(msgKey));
    }
    public static String getMessage(int msgKey) {
    	
    	return rsBundle.getString(String.valueOf(msgKey));
    }

    public static String getMessage(long msgKey, Object[] param) {
        
        return getMessage(String.valueOf(msgKey), param);
    }

    public static String IOS2UTF8(String msg){
        try {
            return new String(msg.getBytes("ISO-8859-1"),"GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("资源key:{0}转换错误",msg);
        }
        return null;
    }
}
