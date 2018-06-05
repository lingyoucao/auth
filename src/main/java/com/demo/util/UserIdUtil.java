package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.zip.CRC32;

/**
 * 随机生成用户ID帮助类
 */
public class UserIdUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserIdUtil.class);

    public static String getUserId(String uuid) {
        String userId = "";
        try {
            CRC32 crc32 = new CRC32();
            crc32.update(uuid.getBytes());
            long value = crc32.getValue();
            String valueStr = "" + value;
            int len = valueStr.length();
            if (len < 10) {
                for (int i = 0; i < 10 - len; i++) {
                    valueStr = "0" + valueStr;
                }
            }
            userId = "591" + valueStr;
            logger.info("随机生成用户ID:" + userId);

        } catch (Exception e) {
            logger.error("生成用户ID失败：", e);
        }
        return userId;
    }
}
