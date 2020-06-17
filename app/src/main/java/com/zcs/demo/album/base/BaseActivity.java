package com.zcs.demo.album.base;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.zcs.demo.album.databinding.DialogLoadingBinding;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity {
    private AlertDialog alertDialog;

    public void showLoading() {
        showLoading("数据加载中...");
    }

    public void showLoading(String msg) {
        alertDialog = new AlertDialog.Builder(this).create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                return true;
            return false;
        });
        alertDialog.show();
        DialogLoadingBinding binding = DialogLoadingBinding.inflate(getLayoutInflater());
        binding.tvLoading.setText(msg);
        alertDialog.setContentView(binding.getRoot());
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void hideLoading() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog = null;
    }
}
