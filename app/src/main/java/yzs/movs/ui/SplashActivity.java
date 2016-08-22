package yzs.movs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import yzs.movs.R;

/**
 * Des：
 * creat by Zishu.Ye on 2016/8/22  23:08
 */
public class SplashActivity extends AppCompatActivity {

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this,SpliceActivity.class));
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_splash);

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                handler.sendMessageDelayed(new Message(),2000);
            }
        };
        runnable.run();


    }
}
