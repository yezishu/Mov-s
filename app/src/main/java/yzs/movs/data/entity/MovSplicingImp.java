package yzs.movs.data.entity;

import com.litesuits.orm.db.annotation.Table;

import yzs.movs.data.MovSplicing;
import yzs.movs.data.SpliceItem;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:23
 */

@Table("mov_splicing")
public class MovSplicingImp extends MovSplicing {

    public String name;

    @Override
    public boolean deleteItem(SpliceItem item) {
        return false;
    }

    @Override
    public boolean insertItem(SpliceItem item) {
        return false;
    }
}
