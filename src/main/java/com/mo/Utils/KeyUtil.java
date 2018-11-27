package com.mo.Utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 格式: 时间 + 随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        //生成1个六位数的随机数
        //Random.nextInt(a)指的是从区间[0, a)中随机选取一个数
        //可以计算出,再加个100000就是[100000, 999999]中的一个随机数
        return System.currentTimeMillis()
                + String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
