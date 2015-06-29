package com.adnTrading.utils;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

public class ReuseableClass {

	public static String baseUrl = "http://bumba27.byethost16.com/";
	
	public static Typeface getFontStyle(Context c) 
	{
		return Typeface.createFromAsset(c.getAssets(),"fonts/gothic.ttf");
	}

	//===================================================================================================================================
	//check Mobile data and wifi
	//===================================================================================================================================
	public static  boolean haveNetworkConnection(Activity myActivity) 
	{
		ConnectivityManager cm = (ConnectivityManager)myActivity.getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//====================================================================================================================================
	//checking Mobile data and wifi END
	//====================================================================================================================================


	//===================================================================================================================================
	//Preference variable
	//===================================================================================================================================

	//--------------------------------------------
	// method to save variable in preference
	//--------------------------------------------
	public static void saveInPreference(String name, String content, Activity myActivity) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(myActivity);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name, content);
		editor.commit();
	}

	//--------------------------------------------
	// getting content from preferences
	//--------------------------------------------
	public static String getFromPreference(String variable_name, Activity myActivity) {
		String preference_return;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(myActivity);
		preference_return = preferences.getString(variable_name, "");

		return preference_return;
	}


	//===================================================================================================================================
	//Preference variable
	//===================================================================================================================================
}
