package yzs.movs.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import yzs.movs.App;
import yzs.movs.R;
import yzs.movs.data.entity.MovSplicingImp;
import yzs.movs.data.factory.MovSplicingFactory;
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

    @Bind(R.id.fabtn) FloatingActionButton fab;
    @OnClick(R.id.btn_save)
    public void save(){
        MovSplicingImp movSplicing= MovSplicingFactory.createMovSplicing();
        App.mDb.save(movSplicing);
    }

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
