package com.adnTrading.parknow;

import com.adnTrading.utils.ReuseableClass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SpashScreen extends Activity {

	LinearLayout logo_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.spash_screen);
		
		
		TextView textViewTitle = (TextView)findViewById(R.id.textViewTitle);
		textViewTitle.setTypeface(ReuseableClass.getFontStyle(SpashScreen.this));		
		
		Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		logo_layout = (LinearLayout)findViewById(R.id.linearLayoutLogoLayout);

		logo_layout.setAnimation(slideUp);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent myIntent = new Intent(SpashScreen.this, LoginActivity.class);
				finish();
				startActivity(myIntent);
				 overridePendingTransition(R.anim.fadein,R.anim.fadeout);
			}
		}, 3500);
	}
	
	
	private void createAndOpenDb() 
	{}
}
