package com.example.zhuwo.daygram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemActivity extends Activity {

	//初始化这么多，好复杂
	//因为这是一个神奇的界面，什么都放进来
	private LinearLayout mLayoutSetting;

	private RelativeLayout mLayoutList;

	private ListView mListView;

	private Button mBtnSetPwd;
	private Button mBtnAdd;
	//没有初始化的时候是灰色的 初始化之后就是加粗紫色
	private Button about_author_Button;

	private EditText mSetPassword;
	private EditText mRepPassword;

	ArrayList<Each_Diary> items;

	ListViewAdapter listViewAdapter;
	DbUtil util;

	private String flag; // 标识是更新还是添加

	public final static String SER_KEY = "cn.gdin.diary.ser";

	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//进入正题，仍然是先设置窗口形式
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_all);

		MyApplication.getInstance().addActivity(this);

		//???????
		initView();
		//???????
		loadView();
		//???????
		initData();
		// 添加日记
		addBtnOnClick();
	}


	// 初始化控件
	private void initView() {
		// 设置密码界面
		mLayoutSetting = (LinearLayout) findViewById(R.id.layout_setting);
		mSetPassword = (EditText) findViewById(R.id.et_set_pwd);
		mRepPassword = (EditText) findViewById(R.id.et_rep_pwd);
		mBtnSetPwd = (Button) findViewById(R.id.btn_set_pwd_ok);

		// 我的日记界面
		mLayoutList = (RelativeLayout) findViewById(R.id.layout_list);
		mListView = (ListView) findViewById(R.id.listView);
		mBtnAdd = (Button) findViewById(R.id.add_diary);
	}


	// 密码不存在，显示设置密码
	private void loadView() {
		//Returns true if the string is null or 0-length.
		//如果editText本身为NULL，那么equals()就会报错
		if (TextUtils.isEmpty(Util.loadData(this)))
		{
			mLayoutSetting.setVisibility(View.VISIBLE);
			SetBtnOnclick();
		}
	}

	// 设置密码
	private void SetBtnOnclick() {
		mBtnSetPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String passwd = mSetPassword.getText().toString();
				String rePassword = mRepPassword.getText().toString();
				if (!passwd.equals(rePassword)) {
					Toast.makeText(ItemActivity.this, "两次密码不一致",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					Util.savaData(ItemActivity.this, passwd);

					mLayoutList.setVisibility(View.VISIBLE);
					mLayoutSetting.setVisibility(View.GONE);
				}
			}
		});
	}

	// 显示listview内容
	private void initData() {
		util = new DbUtil(this);

		items = util.getAllData();

		//adapter是一种什么转化，不懂
		listViewAdapter = new ListViewAdapter(items, this);

		//变成列表，不懂
		mListView.setAdapter(listViewAdapter);

		// 如果有人点击列表的某一项，那么先显示之前编辑的内容
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long line) {
				String id = items.get(position).getId();
				String date = items.get(position).getDate();
				String week = items.get(position).getWeek();
				String content = items.get(position).getContent();
				flag = "1";

				Each_Diary mDiary = new Each_Diary();
				mDiary.setId(id);
				mDiary.setDate(date);
				mDiary.setWeek(week);
				mDiary.setContent(content);

				//flag——有新建和更新之分，存在mainactivity中
				Intent intent = new Intent(ItemActivity.this, MainActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable(SER_KEY, mDiary);
				intent.putExtras(mBundle);
				intent.putExtra("flag", flag);
				startActivity(intent);
			}
		});

		about_author_Button = (Button) findViewById(R.id.about_author_Button);
		about_author_Button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Intent intent = new Intent().setClass(getApplicationContext(),AboutAuthor.class);
				//上下两种表达有什么区别
				Intent intent = new Intent(ItemActivity.this, AboutAuthor.class);
				startActivity(intent);
			}
		});

		// 长按弹出删除提示 这里只是显示提示信息，又不是真的删除
		mListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu arg0, View arg1, ContextMenuInfo arg2)
					{
						arg0.setHeaderTitle("是否删除");
						arg0.add(0, 0, 0, "删除");
						arg0.add(0, 1, 0, "取消");
					}
		});

	}

	// 提示菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// item.getItemId()：点击了菜单栏里面的第几个项目");
		if (item.getItemId() == 0) {
			int selectedPosition = ((AdapterContextMenuInfo) item.getMenuInfo()).position;// 获取点击了第几行

			String id = items.get(selectedPosition).getId();
			// 删除日记
			util.delete(id);
			items.remove(items.get(selectedPosition));
			listViewAdapter.notifyDataSetChanged();

		}

		return super.onContextItemSelected(item);
	}

	// 跳转至添加新日记
	private void addBtnOnClick()
	{
		mBtnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//因为是初次写，flag，是“0”,新建和更新的区别
				flag = "0";

				Intent intent = new Intent();
				//确实要新建的话，就到新建页面中去
				intent.setClass(ItemActivity.this, MainActivity.class);
				//对flag值的设置留着
				intent.putExtra("flag", flag);
				startActivity(intent);
				finish();
			}
		});
	}

	// 复写返回键功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			//设置两次点击的时间间隔为2000时退出有效
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {

				MyApplication.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
