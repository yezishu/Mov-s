package yzs.movs.data;

import java.util.List;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:08
 */
public abstract class MovSplicing extends BaseEntity{
    List<SpliceItem> spliceItems;

    public abstract boolean deleteItem(SpliceItem item);

    public abstract boolean insertItem(SpliceItem item);
}
