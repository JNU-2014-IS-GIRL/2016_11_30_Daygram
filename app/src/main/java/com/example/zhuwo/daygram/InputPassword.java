package com.example.zhuwo.daygram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputPassword extends Activity {
	private Button mBtnPwdOk;
	private Button mBtnCancel;
	private EditText mPassword;
	//pwd是真正的密码
	private String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//开始设定这个activity的窗口
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.insert_pwd);

		MyApplication.getInstance().addActivity(this);
		
		mPassword = (EditText) findViewById(R.id.et_pwd); // 填写密码文本框
		mBtnPwdOk = (Button) findViewById(R.id.btn_pwd_ok);// 确认
		mBtnCancel = (Button) findViewById(R.id.btn_pwd_cancel);// 取消

		// 解密
		//应该是将数据库中存放的正确密码解密拿出来看看，如果没有就注册
		//好奇怪，难道有什么不可逆的设置，必须密码页面是首页？？？不然不是太不安全了
		pwd=Util.loadData(this);
		//下面对解密出来的这个结果进行分析
		//当pwd是空(null)或空串("")时,返回 true，否则返回 false。
		//TextUtils.isEmpty()是系统的函数和方法
		if (!TextUtils.isEmpty(pwd))
		{
			pwdBtnOnclick();
		}
		else
		{
			Intent intent = new Intent();
			//这里也是去item activity，用户注册也去这里，写日记界面也是这里
			intent.setClass(InputPassword.this, ItemActivity.class);
			startActivity(intent);
			finish();
		}

	}

	// 输入密码
	//因为发现系统中有以前保存的密码，所以当用户按下确认键的时候进行密码的确认，按下返回的时候就结束
	private void pwdBtnOnclick() {
		mBtnPwdOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//当前用户的输入
				String password = mPassword.getText().toString();

				if (password.equals(pwd)) {
					Intent intent = new Intent();
					// 一个神奇的界面，又可以进行用户注册，又可以是写日记的界面
					intent.setClass(InputPassword.this, ItemActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(InputPassword.this, "密码错误！", Toast.LENGTH_SHORT).show();
				}

			}
		});
		//用户点击了取消，退出
		mBtnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
