package com.utils.cache;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.sql.Timestamp;
import java.util.Date;

public class TimestampUtil {
    public static Long getDiffDays(Timestamp timestamp1, Timestamp timestamp2) {
        return DateUtil.between(new Date(timestamp1.getTime()), new Date(timestamp2.getTime()), DateUnit.DAY);
    }
}
