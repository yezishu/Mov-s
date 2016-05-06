package yzs.movs.data.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yzs.movs.R;
import yzs.movs.data.MovSplicing;
import yzs.movs.data.SpliceItem;
import yzs.movs.data.options.SpliceOptions;
import yzs.movs.even.MovSpliceCallback;
import yzs.movs.util.IoUtils;
import yzs.movs.util.LogUtil;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:23
 */

@Table("mov_splicing")
public class MovSplicingImp extends MovSplicing {

    public String name;

    @Ignore
    public MovSpliceCallback mMovSpliceCallback;

    @Ignore
    public SpliceOptions mSpliceOptions;

    @Ignore
    public FinishSpliceHelper mFinshSpliceHelper;

    public void attachActivity(MovSpliceCallback movSpliceCallback){
        this.mMovSpliceCallback=movSpliceCallback;
    }

    @Override
    public boolean deleteItem(SpliceItem item) {
        return false;
    }

    @Override
    public boolean insertItem(SpliceItem item) {
        return false;
    }

    @Override
    public boolean splice(Context context) throws Exception{
        if(this.mMovSpliceCallback!=null)
            this.mMovSpliceCallback.prepareSplice();
        LogUtil.log("startSplice...");
        startSplice(context);

        mSpliceOptions.setOptions();

        LogUtil.log("finishSplice...");
        finishSplice(context);

        if(this.mMovSpliceCallback!=null)
            this.mMovSpliceCallback.finishSplice();

        return false;
    }

    /**
     * 开始拼接操作
     * 计算拼接后的图片高度和宽度
     * @param context con
     * @throws Exception 异常
     */
    private void startSplice(Context context)throws Exception{
        if(this.mSpliceOptions ==null)
            this.mSpliceOptions =new SpliceOptions();
        int mTraverseIndex=-1,size[];

        if(mSpliceOptions.isHorizontal){
            int totalWidth=0;
            int maxHeight=-1;
            while ((size=getNextBitmapSize(context,mTraverseIndex))!=null){
                if(size[0]==0&&size[1]==0) break;
                totalWidth+=size[0];
                maxHeight= maxHeight==-1||maxHeight<size[1]?size[1]:maxHeight;
                mTraverseIndex++;
            }
            totalWidth+= mSpliceOptions.space_horizontal*(spliceItems.size()+1);
            maxHeight+= mSpliceOptions.space_vertical*2;
            mSpliceOptions.resultHeight=maxHeight;
            mSpliceOptions.resultWidth=totalWidth;
            LogUtil.log("TotalWidth=%d,Max height=%d",totalWidth,maxHeight);
        }else {
            int maxWith=-1;
            int totalHeight=0;

            while ((size=getNextBitmapSize(context,mTraverseIndex))!=null){
                if(size[0]==0&&size[1]==0) break;
                totalHeight+=size[1];
                maxWith=maxWith==-1||maxWith<size[0]?size[0]:maxWith;
                mTraverseIndex++;
            }
            totalHeight+= mSpliceOptions.space_vertical*(spliceItems.size()+1);
            maxWith+= mSpliceOptions.space_horizontal*2;
            mSpliceOptions.resultHeight=totalHeight;
            mSpliceOptions.resultWidth=maxWith;
            LogUtil.log("TotalHeight=%d,MaxWidth=%d",totalHeight,maxWith);
        }
        if(mSpliceOptions.resultWidth==0
                || mSpliceOptions.resultHeight==0)
            throw new Exception(context.getString(R.string.totalSizeE));
    }

    private int[] getNextBitmapSize(Context context,int mTraverseIndex)throws IOException{
        if(spliceItems==null||spliceItems.size()==0)
            return new int[]{10,10};
        BitmapFactory.Options options=getNextBitmapOptions(context,mTraverseIndex);
        if(options==null)
            return new int[]{0,0};
        return new int[]{options.outWidth,options.outHeight};
    }

    private BitmapFactory.Options getNextBitmapOptions(Context context, int mTraverseIndex)throws IOException{
        mTraverseIndex++;
        if(mTraverseIndex>spliceItems.size()-1)
            return null;
        ImgSpliceItem item=(ImgSpliceItem) spliceItems.get(mTraverseIndex);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        InputStream is;
        is= IoUtils.openStream(context,item.getUri());
        BitmapFactory.decodeStream(is,null,options);
        return options;
    }

    private Bitmap getNextBitmap(Context context,BitmapFactory.Options options,int mTraverseIndex)throws IOException{
        ImgSpliceItem item=(ImgSpliceItem) spliceItems.get(mTraverseIndex);
        InputStream is;
        is=IoUtils.openStream(context,item.getUri());
        return BitmapFactory.decodeStream(is,null,options);
    }


    private void finishSplice(Context context){
        this.mFinshSpliceHelper=new FinishSpliceHelper(context);
        Subscription s= Observable.just(this.mFinshSpliceHelper)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::finishSplice1)
                .observeOn(Schedulers.io())
                .doOnNext(helper->{
                    try {
                       finishSplice2(helper);
                    }catch (IOException ex){
                        throw new RXIOException(ex);
                    }})
                .subscribe(o->{
                    mMovSpliceCallback.finishSplice();},
                    ex->{
                        ex.printStackTrace();
                        mMovSpliceCallback.finishSplice();
                    });



    }

    private FinishSpliceHelper finishSplice1(FinishSpliceHelper helper){
        if(mSpliceOptions.bgColor!= Color.TRANSPARENT){
            helper.mResultCanvas.drawColor(mSpliceOptions.bgColor);
        }
        if(this.mMovSpliceCallback!=null)
            this.mMovSpliceCallback.doSplice();

        return helper;
    }

    /**
     * 做计算图片绘制操作
     */
    private FinishSpliceHelper finishSplice2(FinishSpliceHelper helper)throws IOException{
        final Rect dstRect=new Rect(0,0,10,10);
        int processedCount=0,mTraverseIndex=-1;
        if(this.mSpliceOptions.isHorizontal){
            int currentX=0;
            BitmapFactory.Options bitmapOptions;
            while ((bitmapOptions=getNextBitmapOptions(helper.mContext,mTraverseIndex))!=null){
                processedCount++;
                mTraverseIndex++;

                final int scaledWidth=(int)(bitmapOptions.outWidth*mSpliceOptions.scale);
                final int scaledHeight=(int)(bitmapOptions.outHeight*mSpliceOptions.scale);
                LogUtil.log("current img width=%d , height=%d",bitmapOptions.outWidth,bitmapOptions.outHeight);
                LogUtil.log("scaled img width=%d , height=%d",scaledWidth,scaledHeight);

                dstRect.left=currentX+mSpliceOptions.space_horizontal;
                dstRect.right=dstRect.left+scaledWidth;

                final int centerY=(mSpliceOptions.resultHeight/2)-(mSpliceOptions.space_vertical*2);
                dstRect.top=centerY-(scaledHeight/2);
                dstRect.bottom=centerY+(scaledHeight/2);
                LogUtil.log("left = %d , right = %d ,top = %d , bottom = %d"
                        ,dstRect.left,dstRect.right,dstRect.top,dstRect.bottom);

                bitmapOptions.inJustDecodeBounds=false;
                int reqHeight=dstRect.bottom-dstRect.top;
                if (reqHeight < 1) {
                    bitmapOptions.inSampleSize = 1;
                } else {
                    bitmapOptions.inSampleSize = bitmapOptions.outHeight / reqHeight;
                }

                final Bitmap bm=getNextBitmap(helper.mContext,bitmapOptions,mTraverseIndex);
                if(bm==null) break;
                bm.setDensity(Bitmap.DENSITY_NONE);
                helper.mResultCanvas.drawBitmap(bm,null,dstRect,helper.mPaint);

                currentX=dstRect.right;
                bm.recycle();
            }
        }else {
            int currentY = 0;
            BitmapFactory.Options bitmapOptions;
            while ((bitmapOptions = getNextBitmapOptions(helper.mContext,mTraverseIndex)) != null) {
                processedCount++;
                mTraverseIndex++;

                final int scaledWidth = (int) (bitmapOptions.outWidth * mSpliceOptions.scale);
                final int scaledHeight = (int) (bitmapOptions.outHeight * mSpliceOptions.scale);
                LogUtil.log("CURRENT IMAGE width = %d, height = %d", bitmapOptions.outWidth, bitmapOptions.outHeight);
                LogUtil.log("SCALED IMAGE width = %d, height = %d", scaledWidth, scaledHeight);

                // Top is bottom of the last image plus vertical spacing
                dstRect.top = currentY + mSpliceOptions.space_vertical;
                // Bottom is top plus height of the current image
                dstRect.bottom = dstRect.top + scaledHeight;

                // Centers images horizontally
                final int centerX = (mSpliceOptions.resultWidth / 2) - (mSpliceOptions.space_horizontal * 2);
                dstRect.left = centerX - (scaledWidth / 2);
                dstRect.right = centerX + (scaledWidth / 2);

                LogUtil.log("LEFT = %d, RIGHT = %d, TOP = %d, BOTTOM = %d",
                        dstRect.left, dstRect.right, dstRect.top, dstRect.bottom);

                bitmapOptions.inJustDecodeBounds = false;
                bitmapOptions.inSampleSize = (dstRect.right - dstRect.left) / bitmapOptions.outWidth;

                final Bitmap bm = getNextBitmap(helper.mContext,bitmapOptions,mTraverseIndex);
                if (bm == null) break;
                helper.mResultCanvas.drawBitmap(bm, null, dstRect, helper.mPaint);

                currentY = dstRect.bottom;
                bm.recycle();
            }
        }
        if(processedCount==0)
            helper.mResultBitmap.recycle();


        return finishSplice3(helper);
    }

    /**
     * 保存拼图文件
     * @throws IOException
     */
    private FinishSpliceHelper finishSplice3(FinishSpliceHelper helper)throws IOException{
        File chacheFile=SpliceOptions.makeTempFile(mSpliceOptions.extension);
        LogUtil.log("saving result to %s",chacheFile.getAbsolutePath().replace("%","%%"));
        FileOutputStream os;
        os=new FileOutputStream(chacheFile);
        helper.mResultBitmap.compress(mSpliceOptions.format,mSpliceOptions.quality,os);

        helper.mResultBitmap.recycle();
        os.close();
        return helper;
    }

    /**
     * 拼接结束操作
     * @param helper  h
     */
    private void finishSplice4(FinishSpliceHelper helper){
        mMovSpliceCallback.finishSplice();
    }

    public class FinishSpliceHelper {
        public Context mContext;
        public Bitmap mResultBitmap;
        public Canvas mResultCanvas;
        public Paint mPaint;

        /**
         * 初始化 FinishSpliceHelper
         */
        public FinishSpliceHelper(Context mContext) {
            this.mContext = mContext;

            this.mResultBitmap = Bitmap.createBitmap(
                    mSpliceOptions.resultWidth,
                    mSpliceOptions.resultHeight,
                    Bitmap.Config.ARGB_8888);

            this.mResultCanvas = new Canvas(this.mResultBitmap);
            this.mPaint = new Paint();
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setDither(true);
        }

    }

    public static class RXIOException extends RuntimeException {
        public RXIOException(IOException throwable) {
            super(throwable);
        }
    }

}
