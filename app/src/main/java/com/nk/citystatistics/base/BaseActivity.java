package com.nk.citystatistics.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.nk.citystatistics.R;
import com.nk.citystatistics.dialog.AppProgressDialog;

/**
 * Created by Noman Khan on 27/05/18.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private AppProgressDialog dialog;
    public BaseActivity context;
    private Toolbar toolbar;
    public Context applicationContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        injectDependency();

        applicationContext = getApplicationContext();
    }

    public abstract void injectDependency();

    public abstract int getLayoutId();

    public void showProgressDialog() {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    return;
                }
            }
            dialog = new AppProgressDialog(getContext());
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hideProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public Context getContext() {
        return this;
    }
}

