package com.nk.citystatistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import com.nk.citystatistics.R;

/**
 * Created by Noman Khan on 27/05/18.
 */
public class AppProgressDialog extends Dialog{
    private ImageView layProgress;

    public AppProgressDialog(Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.layout_app_progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }

}
