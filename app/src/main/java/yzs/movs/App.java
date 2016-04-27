package yzs.movs;

import android.app.Application;

import com.litesuits.orm.LiteOrm;

import yzs.movs.debug.DebugConfig;
import yzs.movs.util.ToastUtils;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  15:52
 */
public class App extends Application{

    private static final String DB_NAME="movs";
    public static LiteOrm mDb ;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.register(this);
        mDb=LiteOrm.newCascadeInstance(this,DB_NAME);
        if(DebugConfig.DEBUG){
            mDb.setDebugged(true);
        }

    }
}
