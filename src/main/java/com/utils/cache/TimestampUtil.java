package com.utils.cache;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
public class TimestampUtil {
    public static Long getDiffDays(Timestamp timestamp1, Timestamp timestamp2) {
        Date date1 = new Date(timestamp1.getTime());
        Date date2 = new Date(timestamp2.getTime());
        Long days = DateUtil.between(date1, date2, DateUnit.DAY); // 1
        Long hours = DateUtil.between(date1, date2, DateUnit.HOUR) % 24; // 25
        Long minutes = DateUtil.between(date1, date2, DateUnit.MINUTE) % (60 * 24); // 1439
        Long seconds = DateUtil.between(date1, date2, DateUnit.SECOND) % (60 * 60 * 24); // 86400

        log.warn("days: {}", days);
        log.warn("hours: {}", hours);
        log.warn("minutes: {}", minutes);
        log.warn("seconds: {}", seconds);
        if (hours > 0 || minutes > 0 || seconds > 0) {
            return days + 1;
        }
        return days;
    }
}
