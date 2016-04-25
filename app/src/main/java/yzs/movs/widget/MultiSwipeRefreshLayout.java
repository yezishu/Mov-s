package yzs.movs.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  11:19
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout{

    private CanChildScrollUpCallback mCanchlidScolUpCallback;

    public MultiSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmCanchlidScolUpCallback(CanChildScrollUpCallback mCanchlidScolUpCallback) {
        this.mCanchlidScolUpCallback = mCanchlidScolUpCallback;
    }

    public interface CanChildScrollUpCallback{
        boolean canSwipeRefreshChildScrollup();
    }

    @Override
    public boolean canChildScrollUp() {
        if(this.mCanchlidScolUpCallback!=null){
            return this.mCanchlidScolUpCallback.canSwipeRefreshChildScrollup();
        }
        return super.canChildScrollUp();
    }
}
