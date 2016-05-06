package yzs.movs.data.options;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Des：拼接参数选项
 * creat by Zishu.Ye on 2016/5/5  10:03
 */
public class SpliceOptions {

    public final static String EXTENSION_PNG=".png";

    public double scale;
    public boolean isHorizontal=false;//拼图方向是否横向
    public int space_horizontal;
    public int space_vertical;
    public int resultWidth;
    public int resultHeight;
    public Bitmap.CompressFormat format;//图片编码格式
    public int quality;//图片质量
    public int bgColor= Color.TRANSPARENT;
    public String extension=EXTENSION_PNG;

    public void  setOptions(){
        this.scale=0.50;
        this.space_horizontal=8;
        this.space_vertical=8;
        this.resultWidth=(int)(resultWidth*scale);
        this.resultHeight=(int)(resultHeight*scale);
        this.format= Bitmap.CompressFormat.PNG;
        this.quality=100;
    }

    /**
     * 构造保存拼图的file
     * @param extension 扩展名
     * @return  file
     */
    public static File makeTempFile( String extension)throws IOException{
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format( Calendar.getInstance().getTime());
        File parent = new File(Environment.getExternalStorageDirectory(),"aaa");
        parent.mkdirs();
        return new File(parent, "AFFIX_" + timeStamp + extension);
    }
}
