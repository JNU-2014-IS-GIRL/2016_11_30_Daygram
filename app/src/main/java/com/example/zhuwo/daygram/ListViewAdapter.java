package com.example.zhuwo.daygram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义ListView适配器
 */
public class ListViewAdapter extends BaseAdapter {
	private Context context;
	List<Each_Diary> items = null;

	public ListViewAdapter(List<Each_Diary> items, Context context) {
		this.items = items;
		this.context = context;
	}

	public int getCount() {
		return this.items.size();
	}

	public Object getItem(int paramInt) {
		return null;
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		if (paramView == null) {
			LayoutInflater localLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            paramView = localLayoutInflater.inflate(R.layout.list_item, paramViewGroup, false);
            localLayoutInflater.inflate(R.layout.list_item, paramViewGroup, false);
		}

		//这里获取日期和标题，下面将二者加载到页面
		TextView paramContent = (TextView) paramView.findViewById(R.id.list_diary_content);
		TextView paramDate = (TextView) paramView.findViewById(R.id.list_diary_date);

		//这里怎么灰色了？又不能删掉
		Each_Diary localEachDiaryItems = (Each_Diary) this.items.get(paramInt);
		paramContent.setText(localEachDiaryItems.getContent());
		paramDate.setText(localEachDiaryItems.getDate());

		return paramView;
	}
}
