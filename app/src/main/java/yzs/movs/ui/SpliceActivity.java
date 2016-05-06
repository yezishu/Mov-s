package yzs.movs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.OnClick;
import me.crosswall.photo.pick.PickConfig;
import yzs.movs.R;
import yzs.movs.data.entity.MovSplicingImp;
import yzs.movs.data.factory.MovSplicingFactory;
import yzs.movs.even.ItemTouchEven;
import yzs.movs.even.MovSpliceCallback;
import yzs.movs.even.MyItemTouchCallback;
import yzs.movs.ui.adapter.SplicesAdapterRv;
import yzs.movs.ui.base.SwipeRefreshBaseActivity;
import yzs.movs.util.ExceptionUtil;
import yzs.movs.util.ScreenUtil;
import yzs.movs.util.ToastUtils;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  9:31
 */
public class SpliceActivity extends SwipeRefreshBaseActivity implements ItemTouchEven ,MovSpliceCallback{
    @Override
    protected int provieContentViewId() {
        return R.layout.activity_splice;
    }

    @Bind(R.id.fabtn) FloatingActionButton mFab;
    @Bind(R.id.splicesRv) RecyclerView mSplicesRv;

    private MovSplicingImp mMovSplicing;
    private SplicesAdapterRv mSplicesAdapter;

    private MaterialDialog progress;

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
        mMovSplicing= MovSplicingFactory.createMovSplicing();
        mMovSplicing.attachActivity(this);

        mSplicesRv.setLayoutManager(new LinearLayoutManager(this));
        mSplicesAdapter=new SplicesAdapterRv(mMovSplicing.spliceItems);
        mSplicesRv.setAdapter(mSplicesAdapter);
        ItemTouchHelper.Callback callback=new MyItemTouchCallback(this);
        ItemTouchHelper touchHelper=new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mSplicesRv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splice,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_splice:
                try {
                    mMovSplicing.splice(this);
                }catch (Exception e){
                    ExceptionUtil.showError(this,e);
                    if(progress!=null)
                        progress.dismiss();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
            this.mMovSplicing.spliceItems=mSplicesAdapter.str2model(picks);
            mSplicesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        Collections.swap(this.mMovSplicing.spliceItems,oldPosition,newPosition);
        mSplicesAdapter.notifyItemMoved(oldPosition,newPosition);
    }

    @Override
    public void prepareSplice() {
        ScreenUtil.lockOrientation(this);
    }

    @Override
    public void doSplice() {
        ScreenUtil.unLockOrientation(this);
        progress = new MaterialDialog.Builder(this)
                .content(R.string.splice_process)
                .progress(true, -1)
                .cancelable(false)
                .show();
    }

    @Override
    public void finishSplice() {
        if(progress!=null)
            try{
                progress.dismiss();
            }
            catch (Throwable ing){
                ing.printStackTrace();
            }
    }
}
