package yzs.movs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yzs.movs.R;
import yzs.movs.data.SpliceItem;
import yzs.movs.data.entity.ImgSpliceItem;
import yzs.movs.data.factory.SpliceItemFactory;
import yzs.movs.ui.base.BaseActivity;
import yzs.movs.widget.RatioImageView;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/27  15:33
 */
public class SplicesAdapterRv  extends RecyclerView.Adapter<SplicesAdapterRv.SpliceViewHolder> {

    private static final String TAG="SplicesAdapterRvlog";
    private List<SpliceItem> mSpliceItems;

    public SplicesAdapterRv(List<SpliceItem> spliceItems) {
        this.mSpliceItems = spliceItems;
    }

    @Override
    public SpliceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_splice,parent,false);
        SpliceViewHolder viewHolder=new SpliceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SpliceViewHolder holder, int position) {
        ImgSpliceItem item=(ImgSpliceItem)this.mSpliceItems.get(position);
            holder.setIv(item.path);

    }

    @Override
    public int getItemCount() {
        if(this.mSpliceItems==null)
            return 0;
        return this.mSpliceItems.size();
    }

    @Override
    public void onViewRecycled(SpliceViewHolder holder) {
        super.onViewRecycled(holder);
    }

    class SpliceViewHolder extends RecyclerView.ViewHolder{

        RatioImageView iv;

        public SpliceViewHolder(View itemView) {
            super(itemView);
            iv=(RatioImageView)itemView.findViewById(R.id.iv);
        }

        public void setIv(String path){
            long t= Calendar.getInstance().getTimeInMillis();
            Log.i(TAG,t+"::"+path);
            Glide.with(iv.getContext())
                    .load(path)
                    .placeholder(iv.getDrawable())
                    .override(BaseActivity.getDisplaySize()[0],BaseActivity.getDisplaySize()[0]/3)
                    .error(R.drawable.default_error)
//                    .into(iv);
                    .into(new SimpleTarget<GlideDrawable>() {

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            int size[]=new int[]{200,0};
                            size[1]=resource.getIntrinsicHeight()*size[0]/resource.getIntrinsicWidth();
                            iv.setOriginalSize(size);
                            iv.setImageDrawable(resource);

                        }
                    });
            Log.i(TAG,"end at "+(Calendar.getInstance().getTimeInMillis()-t)+"::"+path);

        }


    }

    /**
     * 将选完的图片path转成model
     * @param paths 选中的图片
     * @return  imgsplice
     */
    public List<SpliceItem> str2model(List<String> paths){
        List<SpliceItem> spliceItems=new ArrayList<>();
        for (String s:paths){
            spliceItems.add(SpliceItemFactory.creatImgSpliceItem(s));
        }
        this.mSpliceItems=spliceItems;
        return spliceItems;
    }
}
