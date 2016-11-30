package com.example.zhuwo.daygram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DbUtil {

	private SQLiteDatabase db;
	private DbHelper dbHelper;

	public DbUtil(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// 插入数据操作
	public void insert(String content) {

		this.open();

		ContentValues cv = new ContentValues();

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("E/MM dd/yyyy");
		String date = sdf.format(d);

		String week = getWeekOfDate(d);

		cv.put(DbHelper.DIARY_DATE, date);
		cv.put(DbHelper.DIARY_WEEK, week);
		cv.put(DbHelper.DIARY_CONTENT, content);

		db.insert(DbHelper.TABLE_NAME, null, cv);
		this.close();
	}

	// 获取星期数
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	// 删除数据操作
	public boolean delete(String id) {
		this.open();
		String where = DbHelper.DIARY_ID + " = ?";
		String[] whereValue = { id };
		if (db.delete(DbHelper.TABLE_NAME, where, whereValue) > 0) {
			this.close();
			return true;
		}
		return false;

	}

	// 更新数据操作
	public boolean update(String id, String content) {
		this.open();
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.DIARY_CONTENT, content);
		if (db.update(DbHelper.TABLE_NAME, cv, DbHelper.DIARY_ID + "=?",
				new String[] { id }) > 0) {
			this.close();
			return true;
		}
		db.close();
		return false;
	}

	// 获取所有数据
	public ArrayList<Each_Diary> getAllData() {
		this.open();
		ArrayList<Each_Diary> items = new ArrayList<Each_Diary>();
		Cursor cursor = db.rawQuery("select * from diary", null);

		if (cursor.moveToFirst()) {

			while (!cursor.isAfterLast()) {
				String id = cursor.getString(cursor
						.getColumnIndex(DbHelper.DIARY_ID));
				String date = cursor.getString(cursor
						.getColumnIndex(DbHelper.DIARY_DATE));
				String week = cursor.getString(cursor
						.getColumnIndex(DbHelper.DIARY_WEEK));
				String content = cursor.getString(cursor
						.getColumnIndex(DbHelper.DIARY_CONTENT));

				Each_Diary item = new Each_Diary(id, date, week, content);
				items.add(item);
				cursor.moveToNext();
			}
		}
		this.close();
		return items;
	}
}
