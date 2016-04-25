package yzs.movs.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;

import butterknife.ButterKnife;
import yzs.movs.R;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  9:48
 */
public abstract class ToolbarActivity  extends BaseActivity{

    abstract protected int provieContentViewId();

    protected AppBarLayout mAppbar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provieContentViewId());
        ButterKnife.bind(this);

        mAppbar=(AppBarLayout)findViewById(R.id.app_bar_layout);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        if(Build.VERSION.SDK_INT>=21){
            mAppbar.setElevation(10.6f);
        }
    }

    protected void hideShowToolbar(){
        mAppbar.animate()
                .translationY(mIsHidden?0:-mAppbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden=!mIsHidden;
    }

}
