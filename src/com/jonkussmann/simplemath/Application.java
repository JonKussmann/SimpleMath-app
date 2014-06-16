package com.jonkussmann.simplemath;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Application extends android.app.Application{
	private static final String TAG = "Application";
	private static SharedPreferences preferences;
	private static final String OPERATION = "operation";
	private static final String SOUND = "sound";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		preferences = getSharedPreferences("com.jonkussmann.simplemath", Context.MODE_PRIVATE);
		Log.d(TAG, "Application onCreate");
	}


		
	
	public static String getOperations() {
		return preferences.getString(OPERATION, "addSubtract");
		
	}
	
	public static void setOperations(String value) {
		preferences.edit().putString(OPERATION, value).commit();
		
	}
	public static boolean getSound() {
		return preferences.getBoolean(SOUND, false);
	}

	public static void setSound(boolean value) {
		preferences.edit().putBoolean(SOUND, value).commit();
	}
}
