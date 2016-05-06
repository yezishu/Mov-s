package yzs.movs.util;

import android.app.Activity;
import android.os.Looper;

import com.afollestad.materialdialogs.MaterialDialog;

import yzs.movs.R;

/**
 * Des：
 * creat by Zishu.Ye on 2016/5/5  11:44
 */
public class ExceptionUtil {
    public static void showError(final Activity context,final Exception e){
        if(Looper.myLooper()!=Looper.getMainLooper()){
            context.runOnUiThread(()-> showError(context,e));
        }
        new MaterialDialog.Builder(context)
                .title(R.string.error)
                .content(e.getMessage())
                .positiveText(android.R.string.ok)
                .show();

    }
}
