package com.sysongy.api.util;

/**
 * @FileName: DistCnvter
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.util
 * @Link: http://www.sysongy.com
 * @Created on: 2016年09月07日, 16:31
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description: 计算两点间的距离（单位：米）
 */

/*
解决此问题的关键是要理解空间几何模型，在理解空间几何模型的基础上再利用数学公式求取两点之间的值。其解决步骤如下：
    1、设两点分别为P1、P2，如果其值是用度分秒形式表示，则需将其转换成十进制度的形式，如P1点纬度为23度30分，则其纬度值转换成十进制度的形式为23.5度。如果值为十进制度的形式，则直接进入第二步。
    2、分别将两点的经度、纬度值转换成弧度制形式，如P1纬度为23.5度，转换成弧度制则为：23.5*PI / 180。分别用 P1latInRad、P1LongInRad、P2latInRad、P2LongInRad表示。
    3、分别求取两点间的纬度差（dlat）与经度差（dlon）；
    4、求取两点间的正弦与余弦值，公式如下：
    A=sin2(dlat/2) + cos(P1LatInRad)*cos(P2LatInRad)*Sin2(dlon/2)       (1)
    5、求取两点的正切值，公式如下：
    C=2*Math.Atan2(Math.Sqrt(A), Math.Sqrt(1-A))                     (2)
    6、返回两点间的距离：公式如下：
    D=EarthRadiusKm * C                                          (3)
*/

public class DistCnvter {
    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static double getDistance(double longt1, double lat1, double longt2,double lat2) {
        double x, y, distance;
        x = (longt2 - longt1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }
}
