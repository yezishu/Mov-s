package yzs.movs.data;

import android.content.Context;

import java.util.List;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:08
 */
public abstract class MovSplicing extends BaseEntity{
    public List<SpliceItem> spliceItems;

    public abstract boolean deleteItem(SpliceItem item);

    public abstract boolean insertItem(SpliceItem item);

    /**
     * 拼接图片
     * @return 操作flag
     */
    public abstract boolean splice(Context context)throws Exception ;
}
