package com.example.zhuwo.daygram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

	private Button mSaveBtn;
	private Button mBackBtn;

	private EditText mTextContent;

	private TextView mTextDate;
	private TextView mTextWeek;

	private String flag;
	private String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.write_diary);

		MyApplication.getInstance().addActivity(this);

		initView();

		Intent intent = getIntent();
		//获取标识，0代表写新日记，1代表查看并修改选中的日记
		flag = intent.getStringExtra("flag");
		if (flag.equals("0")) {
			initDate();
		} else {
			initUpdateData();
		}
		saveOnClick();
		backOnClick();
	}

	// 初始化控件
	public void initView() {
		mSaveBtn = (Button) findViewById(R.id.btn_save);
		mBackBtn = (Button) findViewById(R.id.btn_back);
		mTextContent = (EditText) findViewById(R.id.et_content);
		mTextDate = (TextView) findViewById(R.id.tv_date);
		mTextWeek = (TextView) findViewById(R.id.tv_week);
	}

	// 加载当天日期
	private void initDate() {

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM dd/yyyy");
		String date = sdf.format(d);

		String week = DbUtil.getWeekOfDate(d);
		//设置日期，两个星期几在这里？？
		mTextDate.setText(date);
		mTextWeek.setText(week);
	}

	// 加载要更新的数据
	private void initUpdateData() {
		Each_Diary mDiary = (Each_Diary) getIntent().getSerializableExtra(
				ItemActivity.SER_KEY);
		mId = mDiary.getId();
		mTextDate.setText(mDiary.getDate());
		mTextWeek.setText(mDiary.getWeek());
		mTextContent.setText(mDiary.getContent());

	}

	//改写为添加当前时间的函数
	private void backOnClick() {
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//取得当前时间
		//放到当前的text文本中去

			}
		});

	}

	// 保存日记
	private void saveOnClick() {
		mSaveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DbUtil db = new DbUtil(MainActivity.this);

				String content = mTextContent.getText().toString();
				if (content.equals("")) {
					Toast.makeText(MainActivity.this, "内容不能为空！", Toast.LENGTH_SHORT).show();
				} else {
					if (flag.equals("0")) {
						db.insert(content);
						Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
						//确定保存之后应当返回
						Intent intent = new Intent();
						//跳转
						intent.setClass(MainActivity.this, ItemActivity.class);
						startActivity(intent);
						finish();
					} else {
						db.update(mId,content);
						Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
						//更新成功之后也应当返回
						Intent intent = new Intent();
						//跳转
						intent.setClass(MainActivity.this, ItemActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		});
	}

	// 复写返回键功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			//跳转
			intent.setClass(MainActivity.this, ItemActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return false;
	}
}
