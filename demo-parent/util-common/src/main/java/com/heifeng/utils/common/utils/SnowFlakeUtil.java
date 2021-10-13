package com.heifeng.utils.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @Author: xlf
 * @Date: 2021/06/04/15:28
 * @Description:
 */
public class SnowFlakeUtil {

    /**
     * 派号器workid：0~31
     * 机房datacenterid：0~31
     */
    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 获取雪花算法的id
     * @return
     */
    public static Long nextId() {
        return snowflake.nextId();
    }

}
