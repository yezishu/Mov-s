package yzs.movs.data;

import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.Date;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/25  16:13
 */
public class BaseEntity implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT) public long id;
    @NotNull public Date creatTime;
}
