package com.zcs.demo.album;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zcs.demo.album.databinding.ActivityMainBinding;
import com.zcs.demo.album.usage.AppUsageActivity;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.btnAppUsage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AppUsageActivity.class)));
    }
}
