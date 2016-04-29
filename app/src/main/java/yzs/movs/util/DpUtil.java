package yzs.movs.util;

import android.content.Context;

/**
 * Des：dp 2 px
 * creat by Zishu.Ye on 2016/4/28  11:03
 */
public class DpUtil {

    public static int dp2px(Context context, int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}
