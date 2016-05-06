package yzs.movs.util;

import android.util.Log;

/**
 * Des：
 * creat by Zishu.Ye on 2016/5/5  15:25
 */
public class LogUtil {

    public final static String TAG="Movs";


    public static void log(String message,Object... formatArgs){
        if(formatArgs!=null&&formatArgs.length>0){
            Log.d(TAG,String.format(message,formatArgs));
        }else {
            Log.d(TAG,message);
        }
    }
}
