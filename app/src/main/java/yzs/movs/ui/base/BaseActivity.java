package yzs.movs.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  9:32
 */
public class BaseActivity extends AppCompatActivity{

    private CompositeSubscription mCompositeSubscription;

    private static int[] displaySize;

    public CompositeSubscription getCompositeSubscription(){
        if(this.mCompositeSubscription==null){
            this.mCompositeSubscription=new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription subscription){
        if(this.mCompositeSubscription==null){
            this.mCompositeSubscription=new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    public static int[] getDisplaySize(){
        if(displaySize==null||displaySize[0]==0||displaySize[1]==1){
            displaySize=new int[]{1080,1080/3};
        }
        return displaySize;
    }

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        displaySize =new int[]{dm.widthPixels,dm.heightPixels};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.mCompositeSubscription!=null){
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
