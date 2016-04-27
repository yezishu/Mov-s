package yzs.movs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import yzs.movs.R;
import yzs.movs.data.entity.ImgSpliceItem;
import yzs.movs.data.factory.SpliceItemFactory;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/27  15:33
 */
public class SplicesAdapterRv  extends RecyclerView.Adapter<SplicesAdapterRv.SpliceViewHolder> {

    private List<ImgSpliceItem> mSpliceItems;

    public SplicesAdapterRv(List<ImgSpliceItem> spliceItems) {
        this.mSpliceItems = spliceItems;
    }

    @Override
    public SpliceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_splice,parent,false);
        return new SpliceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpliceViewHolder holder, int position) {
        ImgSpliceItem item=this.mSpliceItems.get(position);
        holder.setIv(item.path);
    }

    @Override
    public int getItemCount() {
        if(this.mSpliceItems==null)
            return 0;
        return this.mSpliceItems.size();
    }



    class SpliceViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;

        public SpliceViewHolder(View itemView) {
            super(itemView);
            iv=(ImageView)itemView.findViewById(R.id.iv);
        }

        public void setIv(String path){
            Glide.with(iv.getContext())
                    .load(path)
                    .placeholder(iv.getDrawable())
                    .error(R.drawable.default_error)
                    .into(iv);
        }
    }

    /**
     * 将选完的图片path转成model
     * @param paths 选中的图片
     * @return  imgsplice
     */
    public List<ImgSpliceItem> str2model(List<String> paths){
        List<ImgSpliceItem> spliceItems=new ArrayList<>();
        for (String s:paths){
            spliceItems.add(SpliceItemFactory.creatImgSpliceItem(s));
        }
        this.mSpliceItems=spliceItems;
        return spliceItems;
    }
}
