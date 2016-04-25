package yzs.movs.data.entity;

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
}
