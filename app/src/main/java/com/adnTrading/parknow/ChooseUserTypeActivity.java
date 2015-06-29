package com.adnTrading.parknow;

import com.adnTrading.utils.ReuseableClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseUserTypeActivity extends Activity {
	
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_user_type_activity);
		
		((Button)findViewById(R.id.buttonOwner)).setTypeface(ReuseableClass.getFontStyle(this));
		((Button)findViewById(R.id.buttonRenter)).setTypeface(ReuseableClass.getFontStyle(this));
	}
	
	@Override 
	public void onBackPressed() 
	{ 
	    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) 
	    {  
	        super.onBackPressed();  
	        return; 
	    } 
	    else { Toast.makeText(getBaseContext(), R.string.tap_to_back, Toast.LENGTH_SHORT).show(); }
	 
	    mBackPressed = System.currentTimeMillis();
	} 
	
	public void goingToOwner(View v) 
	{
		Intent i = new Intent(this, AddParkingSpaceActivity.class);
		startActivity(i);
		finish();
	}
	
	public void goingToRenter(View v) 
	{
		Intent i = new Intent(this, SearchParkingSpaceActivity.class);
		startActivity(i);
		finish();
	}
}
