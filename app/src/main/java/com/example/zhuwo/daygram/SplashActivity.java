package com.example.zhuwo.daygram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //放上第一个activity的布局
        setContentView(R.layout.splash);

        //获取组件
        RelativeLayout splash_view = (RelativeLayout) findViewById(R.id.splash_view);

        //背景透明度变化3秒内从0.3变 到1.0
        //AlphaAnimation是透明度渐变的动画效果，从0（透明）到1（原始亮度）
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(3000);
        splash_view.startAnimation(aa);

        //创建Timer对象
        Timer timer = new Timer();
        //创建TimerTask对象
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, InputPassword.class);
                //intent的内容已经放好了，下面开始进行
                startActivity(intent);
                //跳转完之后，这个就没用了
                //并不是说这个还要留着等待返回，或者再次调用
                finish();
            }
        };
        //使用timer.schedule（）方法调用timerTask，定时3秒后执行run！！！！！！！！
        timer.schedule(timerTask, 3000);
    }
}
