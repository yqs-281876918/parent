package org.mixed.exam.paper.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MathUtil
{
    public static double SGM=0.6746;
    public static double RATIO=169.0971;
    private static double normpdfDouble(double x)
    {
        double ans=(1.0/(Math.sqrt(2*Math.PI)*SGM))*Math.exp((-x*x)/(2*SGM*SGM));
        ans*=RATIO;
        return ans;
    }
    private static Map<Integer,Double> normpdfCache=new HashMap<>();
    public static double normpdfInt(int x)//用x%表示百分比,利于使用缓存提高性能
    {
        if(normpdfCache.containsKey(x))
        {
            return normpdfCache.get(x);
        }
        normpdfCache.put(x,normpdfDouble(x/100.0));
        return normpdfCache.get(x);
    }
    private static Random random = new Random(System.currentTimeMillis());
    public static int getRandomInt(int lower,int upper)
    {
        return random.nextInt(upper-lower+1)+lower;
    }

    public static int daysBetween(Date now, Date past) {
        int difference = (int)((now.getTime()-past.getTime())/86400000);
        return Math.abs(difference);
    }
}
