package com.example.zhuwo.daygram;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {

	//运用list来保存们每一个activity是关键
	private List<Activity> activityList = new LinkedList<Activity>();
	//为了实现每次使用该类时不创建新的对象而创建的静态对象
	private static MyApplication instance;
	//构造方法
	private MyApplication() {}

	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;

	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	//异常处理
	//关闭每一个list内的activity
	public void exit() {
		try {
			for (Activity activity : activityList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	//杀进程
	//这个用到了？怎么不是灰色的？？？
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
}
