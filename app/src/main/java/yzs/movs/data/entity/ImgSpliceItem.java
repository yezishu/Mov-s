package yzs.movs.data.entity;

import android.net.Uri;

import com.litesuits.orm.db.annotation.Table;

import yzs.movs.data.SpliceItem;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:19
 */
@Table("SpliceItem")
public class ImgSpliceItem extends SpliceItem{

    public String path;

    @Override
    public void cut() {

    }

    /**
     * 封装图片路径成 uri
     * @return uri
     */
    public Uri getUri(){
        Uri uri=Uri.parse(path);
        if(!uri.toString().startsWith("file://")
                &&!uri.toString().startsWith("content://"))
            uri=Uri.parse(String.format("file://%s",uri.toString()));
        return uri;
    }
}
