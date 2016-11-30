package com.example.zhuwo.daygram;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AboutAuthor extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_author);

    }

    //删除了以后就崩了 原来并不是灰色的地方就不需要的
    protected void about_author_Return_OnClickListener(View view)
    {
        this.finish();
    }
}
