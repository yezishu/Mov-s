package yzs.movs.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import butterknife.Bind;
import yzs.movs.R;
import yzs.movs.widget.MultiSwipeRefreshLayout;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  11:18
 */
public abstract class SwipeRefreshBaseActivity extends ToolbarActivity {
    @Bind(R.id.swipe_refresh_layout) public MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRequestDataRefresh = false;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefresh();
    }

    void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                    R.color.refresh_progress_2, R.color.refresh_progress_1);
            // do not use lambda!!
            mSwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override public void onRefresh() {
                            requestDataRefresh();
                        }
                    });
        }
    }

    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }

    public void setSwipeableChildren(MultiSwipeRefreshLayout.CanChildScrollUpCallback canChildScrollUpCallback) {
        mSwipeRefreshLayout.setmCanchlidScolUpCallback(canChildScrollUpCallback);
    }
}
