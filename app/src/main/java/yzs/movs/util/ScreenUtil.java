package yzs.movs.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Des：
 * creat by Zishu.Ye on 2016/5/5  10:11
 */
public class ScreenUtil {

    /**
     * 锁定屏幕方向
     * @param con 对应的界面
     */
    public static void lockOrientation(Activity con){
        int orientation;
        int rotation=((WindowManager) con.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getRotation();
        switch (rotation){
            case Surface.ROTATION_0:
                orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case Surface.ROTATION_90:
                orientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
            case Surface.ROTATION_180:
                orientation=ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
            default:
                orientation=ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
        }
        con.setRequestedOrientation(orientation);
    }

    /**
     * 解锁屏幕方向
     * @param con 对应的界面
     */
    public static void unLockOrientation(Activity con){
        con.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
