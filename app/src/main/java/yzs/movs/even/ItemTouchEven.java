package yzs.movs.even;

/**
 * Des：recyclerview  item touch 回调
 * creat by Zishu.Ye on 2016/4/29  14:49
 */
public interface ItemTouchEven {

    /**
     * 当 item 被拖动后回调
     * @param oldPosition
     * @param newPosition
     */
    void itemTouchOnMove(int oldPosition,int newPosition);
}
