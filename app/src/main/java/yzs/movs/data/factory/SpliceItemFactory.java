package yzs.movs.data.factory;

import java.util.Calendar;
import java.util.Date;

import yzs.movs.data.entity.ImgSpliceItem;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:25
 */
public class SpliceItemFactory {

    /**
     * 返回一个 图片拼接item实体
     * @param path
     * @return
     */
    public static ImgSpliceItem creatImgSpliceItem(String path){
        Date time= Calendar.getInstance().getTime();
        ImgSpliceItem imgSpliceItem=new ImgSpliceItem();
        imgSpliceItem.creatTime=time;
        imgSpliceItem.path=path;
        return imgSpliceItem;
    }
}
