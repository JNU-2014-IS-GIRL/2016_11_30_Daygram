package com.example.zhuwo.daygram;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {

	public static final String FILE_NAME_SAVE_DATA = "data.dat";

	//没有加密，只是存取而已
	/*保存密码*/
	public static void savaData(Context context, String password) {
		FileOutputStream fos = null;
		try
		{
			fos = context.openFileOutput(FILE_NAME_SAVE_DATA,
					Context.MODE_PRIVATE);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeBytes(password);
		}
		catch (Exception e) {e.printStackTrace();}
		finally
		{
			try {if (fos != null) fos.close();}
			catch (IOException e) {e.printStackTrace();}
		}
	}

	/*读取用户密码*/
	public static String loadData(Context context) {
		FileInputStream fis = null;

		String password = "";
		try {
			fis = context.openFileInput(FILE_NAME_SAVE_DATA);

			DataInputStream dis = new DataInputStream(fis);
			byte[] b = new byte[1024];
			int len = dis.read(b);

			password = new String(b, 0, len);

		}
		catch (Exception e) {e.printStackTrace();}
		finally
		{
			try {if (fis != null) fis.close();}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return password;
	}
}
