package yzs.movs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.crosswall.photo.pick.PickConfig;
import yzs.movs.R;
import yzs.movs.data.entity.ImgSpliceItem;
import yzs.movs.ui.adapter.SplicesAdapterRv;
import yzs.movs.ui.base.SwipeRefreshBaseActivity;
import yzs.movs.util.ToastUtils;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  9:31
 */
public class SpliceActivity extends SwipeRefreshBaseActivity {
    @Override
    protected int provieContentViewId() {
        return R.layout.activity_splice;
    }

    @Bind(R.id.fabtn) FloatingActionButton mFab;
    @Bind(R.id.splicesRv) RecyclerView mSplicesRv;

    private List<ImgSpliceItem> mSpliceItems=new ArrayList<>();
    private SplicesAdapterRv mSplicesAdapter;

    @OnClick(R.id.fabtn)
    public void fabClick(){
        new PickConfig.Builder(SpliceActivity.this)
                .pickMode(PickConfig.MODE_MULTIP_PICK)
                .maxPickSize(12)
                .spanCount(3)
                .toolbarColor(R.color.colorPrimary)
                .build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRv();
    }

    private void initRv(){
        mSplicesRv.setLayoutManager(new LinearLayoutManager(this));
        mSplicesAdapter=new SplicesAdapterRv(mSpliceItems);
        mSplicesRv.setAdapter(mSplicesAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            ToastUtils.showLong("resultcode:"+resultCode+"  选取失败");
            return;
        }
        if(requestCode==PickConfig.PICK_REQUEST_CODE){
            ArrayList<String> picks=data.getStringArrayListExtra(PickConfig.EXTRA_STRING_ARRAYLIST);
            ToastUtils.showLong("count:"+picks.size()+" 选取成功");
            mSpliceItems=mSplicesAdapter.str2model(picks);
            mSplicesAdapter.notifyDataSetChanged();
        }
    }


}
