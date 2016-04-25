package yzs.movs.data.factory;

import java.util.Calendar;
import java.util.Date;

import yzs.movs.data.entity.MovSplicingImp;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:31
 */
public class MovSplicingFactory {

    /**
     * @return  创建一个图片拼接对象
     */
    public static MovSplicingImp createMovSplicing(){
        Date date= Calendar.getInstance().getTime();

        MovSplicingImp imp=new MovSplicingImp();
        imp.creatTime=date;
        imp.name="S"+date;

        return  imp;
    }
}
