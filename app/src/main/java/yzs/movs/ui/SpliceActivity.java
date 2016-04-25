package yzs.movs.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import butterknife.Bind;
import yzs.movs.R;
import yzs.movs.ui.base.SwipeRefreshBaseActivity;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  9:31
 */
public class SpliceActivity extends SwipeRefreshBaseActivity {
    @Override
    protected int provieContentViewId() {
        return R.layout.activity_splice;
    }

    @Bind(R.id.fabtn)
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );



    }
}
