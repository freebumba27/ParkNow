package com.adnTrading.parknow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.adnTrading.utils.ReuseableClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class GiveReviewPopup extends Activity {
	
	EditText editTextReview;
	RatingBar ratingBarReview;
	private ProgressDialog dialog;
	String parking_spot_id = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.give_review_popup);
		
		editTextReview  = (EditText)findViewById(R.id.editTextReview);
		ratingBarReview = (RatingBar)findViewById(R.id.ratingBarReview);
		
		((Button)findViewById(R.id.buttonSave)).setTypeface(ReuseableClass.getFontStyle(this));
		((TextView)findViewById(R.id.textViewTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		editTextReview.setTypeface(ReuseableClass.getFontStyle(this));

		Bundle extras = getIntent().getExtras(); 
		if (extras != null) 
		{
			parking_spot_id = extras.getString("parking_spot_id");
		}
		
		
	}
	
	public void savingReview(View v) 
	{
		if(ReuseableClass.haveNetworkConnection(this))
		{
			if(editTextReview.getText().toString().trim().equalsIgnoreCase(""))
			{
				Toast.makeText(this, R.string.all_field_mandatory, Toast.LENGTH_SHORT).show();
			}
			else
			{
					dialog = new ProgressDialog(this);
					dialog.setMessage(this.getString(R.string.wait_a_min_dialog_message));
					dialog.show();

					new UserReviewTask().execute(parking_spot_id, editTextReview.getText().toString().trim(), ratingBarReview.getRating()+"", ReuseableClass.getFromPreference("user_id", GiveReviewPopup.this));
			}
		}
		else
		{
			Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
		}
	}
	
	private class UserReviewTask extends AsyncTask<String, String, String> 
	{
		protected String doInBackground(String... values) 
		{
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ReuseableClass.baseUrl + "parknow_api/give_user_review.php");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
				nameValuePairs.add(new BasicNameValuePair("parking_spot_id", values[0]));
				nameValuePairs.add(new BasicNameValuePair("review", values[1]));
				nameValuePairs.add(new BasicNameValuePair("review_star", values[2]));
				nameValuePairs.add(new BasicNameValuePair("user_id", values[3]));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);

				int responseCode = response.getStatusLine().getStatusCode();
				if(responseCode == 200)
				{
					responseBody = EntityUtils.toString(response.getEntity());
				}
			} 
			catch (Exception t) 
			{
				Log.e("TAG", "Error: " + t);
			} 
			return responseBody;
		}

		protected void onPostExecute(String result) 
		{
			Log.d("TAG", "value: " + result);
			if(result.equalsIgnoreCase("YES"))
			{
				Toast.makeText(GiveReviewPopup.this, R.string.review_successful_message, Toast.LENGTH_SHORT).show();
				finish();
			}
			else if(result.equalsIgnoreCase("NO"))
			{
				Toast.makeText(GiveReviewPopup.this, R.string.server_error, Toast.LENGTH_SHORT).show();
				finish();
			}
			else if(result.equalsIgnoreCase("EXISTS"))
			{
				Toast.makeText(GiveReviewPopup.this, R.string.user_review_already_exists_error, Toast.LENGTH_SHORT).show();
				finish();
			}
			else
			{
				Toast.makeText(GiveReviewPopup.this, R.string.other_error, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
