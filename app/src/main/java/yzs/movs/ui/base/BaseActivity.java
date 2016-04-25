package yzs.movs.ui.base;

import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  9:32
 */
public class BaseActivity extends AppCompatActivity{

    private CompositeSubscription mCompositeSubscription;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.mCompositeSubscription!=null){
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
