package com.nk.citystatistics.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Noman Khan on 28/05/18.
 */
public class ToastUtils {

    public static void showToastMessageNormal(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}
