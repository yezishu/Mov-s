package yzs.movs.util;

import android.content.Context;
import android.net.Uri;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Des：
 * creat by Zishu.Ye on 2016/5/5  10:44
 */
public class IoUtils {

    /**
     * 打开图片流
     * @param context  context
     * @param uri   uri
     * @return  图片流
     * @throws FileNotFoundException
     */
    public static InputStream openStream(Context context, Uri uri) throws FileNotFoundException {
        if (uri == null) return null;
        if (uri.getScheme() == null || uri.getScheme().equalsIgnoreCase("file")) {
            return new FileInputStream(uri.getPath());
        } else {
            return context.getContentResolver().openInputStream(uri);
        }
    }
}
